package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import utility.RabinKarp;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

public class RabinKarpDriver {
    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 1)
    @Fork(1)
    @Timeout(time = 30, timeUnit = TimeUnit.SECONDS)
    public void test() throws FileNotFoundException{
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

        for (int i = 0 ; i < words.size(); i++){
            if (RabinKarp.run(words.get(i), pattern, 11)){
                ans.add(i);
            }
        }
    }
}
