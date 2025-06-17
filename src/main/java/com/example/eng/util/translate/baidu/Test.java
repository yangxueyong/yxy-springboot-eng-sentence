package com.example.eng.util.translate.baidu;

import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/yxy/tmp/eng-1.txt";
        List<String> dataLineList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (StrUtil.isEmptyIfStr(line)) {
                    continue;
                }
                if (StrUtil.trim(line).equals("参考答案")) {
                    continue;
                }
                try {
                    String str = line.replace(" ", "");
                    Integer.parseInt(str);
                    continue;
                } catch (Exception e) {

                }
//                System.out.println(line);
                dataLineList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter writer = new FileWriter("/Users/yxy/tmp/eng-sql.txt");

        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        AtomicInteger wordInteger = new AtomicInteger(0);
        AtomicInteger wordRelInteger = new AtomicInteger(0);
        Map<String, String> wordMap = new HashMap<>();

        for (int i = 0; i < dataLineList.size(); i++) {
            String s = dataLineList.get(i);
            if (i == 0 || i % 21 == 0) {
//                System.out.println(s);
                int mainId = atomicInteger.incrementAndGet();
                s = s.replace("'", "''");
                String mainIdStr = String.format("%05d", mainId);
                writer.write("INSERT INTO english_sentence.t_eng_sentence_main (id, en_name, zn_name, sort) \n" +
                        "VALUES('" + mainIdStr + "', '" + s + "', null, '" + mainIdStr + "');\n");
                for (int j = 1; j <= 10; j++) {
                    int i2 = atomicInteger2.incrementAndGet();
                    String znline = dataLineList.get(i + j);
                    String enline = dataLineList.get(i + 10 + j);
                    String[] words = enline.replace("/", " ").replace(" '","'").split(" ");
                    enline = enline.replace("'", "''");
                    String detailSort = String.format("%05d", j);
                    String detailIdStr = String.format("%05d", i2);
                    writer.write("INSERT INTO english_sentence.t_eng_sentence_detail (id, main_id, en_name, zn_name, sort) \n" +
                            "VALUES('" + detailIdStr + "', '" + mainIdStr + "', '" + enline + "', '" + znline + "', '" + detailSort + "');\n");

                    for (int k = 0; k < words.length; k++) {
                        String word = words[k];
                        word = word.replace("'", "''");
                        word = word.replace(" ", "");
                        word = word.replace(",", "");
                        word = word.replace(".", "");
                        word = word.replace("?", "");
                        word = word.replace("(", "");
                        word = word.replace(")", "");
                        if (StrUtil.isEmptyIfStr(word)) {
                            continue;
                        }

                        if (!wordMap.containsKey(word)) {
                            int wordIntId = wordInteger.incrementAndGet();
                            String wordIntIdStr = String.format("%07d", wordIntId);
                            String wordSort = String.format("%05d", k);
                            wordMap.put(word, wordIntIdStr);
                            String zhName = null;
                            try {
                                BaiDuTransEntity translate = BaiDuTransUtil.translate(word, "en", "zh");
                                zhName = translate.getTrans_result().get(0).getDst();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            writer.write("INSERT INTO english_sentence.t_eng_sentence_word (id, zn_name, en_name, sort, local_audio_path) " +
                                    " VALUES('" + wordIntIdStr + "', '"+zhName+"', '" + word + "', '" + wordSort + "', NULL);\n");
                        }
                        String wordIntIdStr = wordMap.get(word);

                        int wordRelIntId = wordRelInteger.incrementAndGet();
                        String wordRelIntIdStr = String.format("%07d", wordRelIntId);

                        writer.write("INSERT INTO english_sentence.t_eng_sentence_detail_word_rel (id, main_id, detail_id, word_id, sort) " +
                                "VALUES('" + wordRelIntIdStr + "', '" + mainIdStr + "', '" + detailIdStr + "', '" + wordIntIdStr + "', '" + k + "');\n");
                    }
                }
            }
        }

        writer.close();


    }
}
