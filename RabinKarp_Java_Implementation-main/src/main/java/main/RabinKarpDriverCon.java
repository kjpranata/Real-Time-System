package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import utility.RabinKarp;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

public class RabinKarpDriverCon {    
    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 1)
    @Fork(1)
    @Timeout(time = 30, timeUnit = TimeUnit.SECONDS)
    public void test2() throws Exception{
        String pattern = "kaloso";
        File file = new File("input3.txt");
        String line = null;
        String text;
        ArrayList<String> words = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            while((line = reader.readLine()) != null){
                words.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<Integer> ans = new ArrayList<>();
        int core = Runtime.getRuntime().availableProcessors();
        ExecutorService ex = Executors.newFixedThreadPool(core);
        int section = words.size()/core;
        ArrayList<Future<ArrayList<Integer>>> results = new ArrayList<>();
        int count = 0;
        
        for (int i = 0; i < words.size() ; i+=section){
            if(i+section-1> words.size()){
                logic l = new logic(i, words.size(), words, pattern);
                results.add(ex.submit(l));
            }
            else{
                logic l = new logic(i, i+section, words, pattern);
                results.add(ex.submit(l));
            }
            count++;
        }
        ex.shutdown();
        
        for (Future<ArrayList<Integer>> future : results){
            List<Integer> result = future.get();
            for (int i : result){
                ans.add(i);
            }
        }
    }
    
    static class logic implements Callable <ArrayList<Integer>>{
        int start;
        int end;
        ArrayList<String> words;
        String pattern;

        public logic(int start, int end, ArrayList<String> words, String pattern) {
            this.start = start;
            this.end = end;
            this.words = words;
            this.pattern = pattern;
        }
        
        @Override
        public ArrayList<Integer> call() throws Exception {
            ArrayList<Integer> ans = new ArrayList<>();
            for(int i = start; i < end; i++){
                if (RabinKarp.run(words.get(i), pattern, 11)){
                    ans.add(i);
                }
            }
            return ans;
        }
    }
}
