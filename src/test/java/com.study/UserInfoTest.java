package com.study;

import com.study.entity.UserInfo;
import com.study.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.*;

@SpringBootTest
public class UserInfoTest {
    @Autowired
    private UserInfoMapper userInfoMapper;

    private final static Integer MAX_DATA_SIZE = 10 * 10000;

    /**
     * 测试查询用户信息
     */
    @Test
    public void testSelectUserInfoByIds() {
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoByIds(Collections.singletonList(1));
        System.out.println(userInfos);
    }

    /**
     * 测试批量插入用户信息
     */
    @Test
    public void testBatchInsert() {
        List<UserInfo> prepareData = new ArrayList<>();
        for (int i = 0; i < MAX_DATA_SIZE; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName("name_" + i);
            userInfo.setAge(i);
            userInfo.setEmail("email_" + i + "@qq.com");
            userInfo.setAddress("address_" + i);

            prepareData.add(userInfo);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 根据需要调整批量大小
        int batchSize = 1000;
        for (int i = 0; i < prepareData.size(); i += batchSize) {
            int end = Math.min(i + batchSize, prepareData.size());
            List<UserInfo> batch = prepareData.subList(i, end);
            // 提交任务给线程池
            userInfoMapper.batchInsertUserInfo(batch);
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }

    /**
     *
     */
    @Test
    public void testDeleteUserInfo() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        int deleteCounts = userInfoMapper.deleteUserInfoByIds(ids);
        Assert.isTrue(Objects.equals(deleteCounts, ids.size()), "删除失败");
    }

    /**
     * 测试多线程插入用户信息
     */
    @Test
    public void multiThreadInsert() throws InterruptedException {
        // 模拟数据
        // 单线程环境下，插入1w条数据 耗时：1084ms, 10w条数据 耗时：3038ms(for循环分批插入耗时：2832ms)
        // 多线程环境下，插入1w条数据 耗时：584ms, 10w条数据 耗时：1672ms
        List<UserInfo> prepareData = new ArrayList<>();
        for (int i = 0; i < MAX_DATA_SIZE; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName("name_" + i);
            userInfo.setAge(i);
            userInfo.setEmail("email_" + i + "@qq.com");
            userInfo.setAddress("address_" + i);

            prepareData.add(userInfo);
        }

        ExecutorService executor = Executors.newFixedThreadPool(20);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 根据需要调整批量大小
        int batchSize = 1000;
        // 计数器
        for (int i = 0; i < prepareData.size(); i += batchSize) {
            int finalI = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                int end = Math.min(finalI + batchSize, prepareData.size());
                List<UserInfo> batch = prepareData.subList(finalI, end);
                userInfoMapper.batchInsertUserInfo(batch);
            }, executor);
            futures.add(future);
        }
        // 等待所有任务完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join(); // 阻塞直到所有任务完成

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }
}
