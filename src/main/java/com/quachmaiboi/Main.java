package com.quachmaiboi;

import com.quachmaiboi.service.CalculatedMetric;
import com.quachmaiboi.service.CalculatedMetricImpl;
import com.quachmaiboi.service.GithubService;

public class Main implements Runnable
{
    private String fromDate;
    private String toDate;

    public void run(){
        GithubService.init();
        CalculatedMetric calculatedMetric = new CalculatedMetricImpl();
        calculatedMetric.calculatedMetricImpl(fromDate, toDate );
        System.out.println("Quach Mai Boi");
    }


    public static void main(String[] args){
        Main main = new Main();
        main.fromDate = args[0];
        main.toDate = args[1];
        new Thread(main).start();
    }
}
