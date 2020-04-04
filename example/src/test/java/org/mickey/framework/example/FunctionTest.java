package org.mickey.framework.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionTest {

    @Test
    public void test1() {
        function1(188, "50 42 9 15 105 63 14 30");
    }

    @Test
    public void test2() {
        function2("1 1", "HI");
        function2("3 8", "HAPPY");
        function2("2 14", "I LOVE YOU");
    }

    public void function1(int max, String priceStr) {
        // 读取转换后的价格集合
        List<Integer> priceArr = parseToList(priceStr);
        // 从小到大排序
        priceArr.sort(Integer::compareTo);
        // 不超预算情况下的总价格
        int money = 0;
        for (int i = 1; i < priceArr.size(); i++) {
            // 下一种情况下的总价
            int next = priceArr.subList(0, i).stream().mapToInt(x -> x).sum();
            // 下一种情况下超出预算，即返回当前总价（不超预算情况下的总价）
            if (next > max) {
                print(money);
                return;
            } else {
                money = next;
            }
        }
    }

    // 将带有空格的单价字符串转换为int 列表
    private List<Integer> parseToList(String priceStr) {
        String[] strings = priceStr.split(" ");
        List<Integer> priceArr = new ArrayList<>();
        for (String s : strings) {
            try {
                int parseInt = Integer.parseInt(s);
                priceArr.add(parseInt);
            } catch (NumberFormatException e) {
                print("输入的单价格式错误，请检查输入");
            }
        }
        return priceArr;
    }


    public void function2(String date, String data) {
        // 原始的编码集合
        List<String> origin = Arrays.asList("ABCDEFGHI", "JKLMNOPQR", "STUVWXYZ*");

        // 获取输入的日期，并拆解成月份和日子
        String[] strings = date.split(" ");
        int month = Integer.parseInt(strings[0]);
        int day = Integer.parseInt(strings[1]);

        // 月份左移量
        int monthInx = (month - 1) % 3;
        // 日子左移量
        int dayInx = (day - 1) % 9;
        // 先计算月份左移
        List<String> current = new ArrayList<>();
        current.addAll(origin.subList(monthInx, origin.size()));
        current.addAll(origin.subList(0, monthInx));

        // 再计算日子左移
        for (int i = 0; i < current.size(); i++) {
            String temp = moveToLeft(current.get(i), dayInx);
            current.set(i, temp);
        }

        // 找出输入字符串每个字符所在的位置
        StringBuffer code = new StringBuffer();
        for (char c : data.toCharArray()) {
            // 字符所在的组序列
            int code1 = 0;
            // 字符所在组中的序列
            int code2 = 0;
            // 将空格替换为 *
            if (" ".equalsIgnoreCase(String.valueOf(c))) {
                c = '*';
            }
            // 遍历字符串信息，找出对应的组和组中序列
            for (int i = 0; i < current.size(); i++) {
                String item = current.get(i);
                if (item.contains(String.valueOf(c))) {
                    code1 = i + 1;
                    code2 = item.indexOf(String.valueOf(c)) + 1;
                }
            }
            code.append(String.valueOf(code1) + code2 + " ");
        }
        // 去掉末尾的最后一个字符
        code.replace(code.length() - 1, code.length(), "");
        print(code);
    }

    private String moveToLeft(String str, int position) {
        return str.substring(position) + str.substring(0, position);
    }

    private void print(Object object) {
        System.out.println(JSON.toJSONString(object, SerializerFeature.PrettyFormat));
    }
}
