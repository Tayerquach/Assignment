package com.quachmaiboi.service;

import com.quachmaiboi.dto.Event;
import com.quachmaiboi.dto.Metric;
import com.quachmaiboi.dto.ResultDto;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CalculatedMetricImpl implements CalculatedMetric
{
    private ExecutorService executorService;
    int totalDays = 0;

    @Override
    public void calculatedMetricImpl(String fromDate, String toDate)
    {
        totalDays = 0;
        try
        {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date from =inputFormat.parse(fromDate);
            Date to =  inputFormat.parse(toDate);
            totalDays = (int) ((to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000));

            if (totalDays <= 0)
            {
                System.out.println("wrong input time!");
                return;
            }
        } catch (Exception e)
        {
            System.out.println("wrong input time!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH);
        LocalDate dateStep = LocalDate.parse(fromDate, formatter);
        LocalDate dateTime = dateStep.minusDays(1);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);




        executorService = Executors.newFixedThreadPool(totalDays * 24);


        try
        {

            for (int i = 0; i < totalDays; i++)
            {
                dateTime = dateTime.plusDays(1);
                String date = format.format(dateTime);
                for (int j = 0; j < 24; j++)
                {
                    String filePath = date + "-" + j + ".json.gz";
                    executorService.submit(() ->
                    {
                        try
                        {
                            GithubService.getInstance().download(filePath);
                        } catch (IOException e)
                        {
                            System.out.println(e.getMessage());
                        }
                    });


                }
            }
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        AtomicInteger count = new AtomicInteger();
        List<Metric> repoDtos = GithubService.getInstance().getRepositories().entrySet().stream()
                .map(p ->
                {
                    ResultDto resultDto = p.getValue();
                    Metric metric = new Metric();

                    Double numberCommit = (double) (resultDto.getTotalCommits()/totalDays);
                    Double numberRelease = (double) (resultDto.getReleaseNumber());

                    metric.setNumberCommit(numberCommit);
//                    metric.setNumberRelease(numberRelease);

                    long maxCommit = 0;
                    if(resultDto.getPushEvents() !=null) {


                        Map<Integer, List<Event>> a = resultDto.getPushEvents().stream().collect(Collectors.groupingBy(Event::getCreateAtDay));

                        List<Event> push = a.entrySet().stream().map(k -> {
                            AtomicLong sum = new AtomicLong();
                            k.getValue().forEach(x -> sum.addAndGet(x.getNumberCommit()));
                            long tt = sum.longValue();
                            return new Event(k.getKey(), tt, new Date());
                        }).sorted(Comparator.comparing(Event::getNumberCommit).reversed())
                                .collect(Collectors.toList());
                        maxCommit = push.get(0).getNumberCommit();
                    }
                    metric.setMaxNumberCommit(maxCommit);
                    Double commitMetric = resultDto.getTotalCommits() > 0 && maxCommit>0 ? Double.valueOf(numberCommit/ maxCommit) : Double.valueOf(0);
//                    Double releaseMetric = resultDto.getReleaseNumber() > 0 ? Double.valueOf(numberRelease) : Double.valueOf(0);
                    Double healthScore = commitMetric;
                    metric.setOrgName(resultDto.getOrgName());
                    metric.setRepoName(resultDto.getRepoName());
                    metric.setHealthScore(healthScore);
                    count.getAndIncrement();
                    System.out.println(count.get());

                    return metric;
                })
                .sorted(Comparator.comparing(Metric::getHealthScore).reversed())
//        Output the top 1000 projects ranked by healthiest to least healthy
                .limit(1000)
                .collect(
                        Collectors.toList());
        try
        {
            toCSV(repoDtos);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void toCSV(List<Metric> data) throws IOException
    {
        FileWriter csvWriter = new FileWriter("health_scores.csv");


        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(Metric.class).withHeader();
        csvWriter.append(csvMapper.writer(schema).writeValueAsString(data));
        csvWriter.flush();
        csvWriter.close();

    }


}
