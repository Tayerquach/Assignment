package com.quachmaiboi.service;


import com.google.gson.Gson;
import com.quachmaiboi.dto.*;
import com.quachmaiboi.dto.Event;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;


// References: https://github.com/LoganPhan/QuoAI-challange-2
public class GithubService
{
    private Map<Long, ResultDto> repositories;
    private static GithubService apiService;
    private static final String GITHUB_DOMAIN = "https://data.gharchive.org/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
    private Gson gson;
    private GithubService() {
        gson = new Gson().newBuilder().create();
        repositories = new ConcurrentHashMap<>();
    }

    public static void init() {
        if (apiService == null) {
            apiService = new GithubService();
        }
    }

    public static GithubService getInstance() {

        return apiService;
    }

    public void download(String suffix) throws IOException
    {
        GZIPInputStream gzip = null;
        BufferedReader br = null;
        HttpURLConnection con = null;
        try {

            URL url = new URL(GITHUB_DOMAIN + suffix);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setAllowUserInteraction(true);
            con.setRequestProperty("Content-type", "application/gzip");
            gzip = new GZIPInputStream(con.getInputStream());
            br = new BufferedReader(new InputStreamReader(gzip));
            String content;

            while ((content = br.readLine()) != null) {
                if(content.contains("\"type\":\"PushEvent\"")) {
                    PushEvent pushEvent = gson.fromJson(content,PushEvent.class);
                    Repo repo = pushEvent.getRepo();
                    Actor actor = pushEvent.getActor();
                    ResultDto resultDto = new ResultDto();
                    if(repo!=null && !repo.getName().isEmpty()){
                        resultDto.setRepoName(repo.getName());
                        resultDto.setId(repo.getId());
                    }
                    if(actor!=null && !actor.getLogin().isEmpty()){
                        resultDto.setOrgName(actor.getLogin());
                        resultDto.setOrgId(actor.getId());

                    }

                    long commits = pushEvent.getPayload().getSize();
                    repositories.compute(repo.getId(), (key, val) -> {
                        if (val == null) {
                            resultDto.setMaxNumberCommit(commits);
                            List<Event> pushEvents = new ArrayList<Event>();
                            Event event = new Event(repo.getId(), commits, pushEvent.getCreatedAt());
                            pushEvents.add(event);
                            resultDto.setPushEvents(pushEvents);
//                            System.out.println("max:" + resultDto.getMaxNumberCommit());
                            return resultDto;
                        } else {
                            Event event = new Event(repo.getId(),commits,pushEvent.getCreatedAt());
                            val.getPushEvents().add(event);
                            val.setTotalCommits(val.getTotalCommits() + commits);
//                            System.out.println("max:" + val.getMaxNumberCommit());
                            return val;
                        }
                    });
                }
                else if(content.contains("\"type\":\"ReleaseEvent\"")) {
                    ReleaseEvent releaseEvent = gson.fromJson(content,ReleaseEvent.class);
                    Repo repo = releaseEvent.getRepo();
                    Actor actor = releaseEvent.getActor();
                    Payload payload = releaseEvent.getPayload();
                    ResultDto resultDto = new ResultDto();

                    if(repo!=null && !repo.getName().isEmpty()){
                        resultDto.setId(repo.getId());
                        resultDto.setRepoName(repo.getName());
                    }
                    if(actor!=null && !actor.getLogin().isEmpty()){
                        resultDto.setOrgName(actor.getLogin());
                        resultDto.setOrgId(actor.getId());
                    }
                    System.out.println("repoId" + repo.getId());
                    repositories.compute(resultDto.getId(), (key, val) -> {
                        if (val == null) {
                            resultDto.setReleaseNumber(Long.valueOf(0L));
                            return resultDto;
                        } else {
                            if(payload != null) {
                                val.setReleaseNumber(val.getReleaseNumber() + 1);
                            }

                            return val;
                        }
                    });
                }
            }
            gzip.close();
            br.close();
            con.disconnect();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - " + suffix);
        }
        finally {
            if(gzip!=null) {
                gzip.close();
            }
            if(br!=null) {
                br.close();
            }
            if(con!=null) {
                con.disconnect();
            }
        }
    }

    public Map<Long, ResultDto> getRepositories() {

        return repositories;
    }
}
