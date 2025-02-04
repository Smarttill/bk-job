/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-JOB蓝鲸智云作业平台 is licensed under the MIT License.
 *
 * License for BK-JOB蓝鲸智云作业平台:
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.tencent.bk.job.common.util;

import com.tencent.bk.job.common.context.JobContext;
import com.tencent.bk.job.common.context.JobContextThreadLocal;
import io.micrometer.core.instrument.Tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 6/11/2019 10:27
 */
public class JobContextUtil {

    public static JobContext getContext() {
        return JobContextThreadLocal.get();
    }

    public static void setContext(JobContext jobContext) {
        JobContextThreadLocal.set(jobContext);
    }

    public static void unsetContext() {
        JobContextThreadLocal.unset();
    }

    public static Long getStartTime() {
        JobContext jobContext = JobContextThreadLocal.get();
        Long startTime = null;
        if (jobContext != null) {
            startTime = jobContext.getStartTime();
        } else {
            return 0L;
        }

        return startTime;
    }

    public static void setStartTime() {
        JobContext jobContext = getOrInitContext();
        jobContext.setStartTime(System.currentTimeMillis());
    }

    public static String getUsername() {
        JobContext jobContext = JobContextThreadLocal.get();
        String staffName = null;
        if (jobContext != null) {
            staffName = jobContext.getUsername();
        }
        return staffName;
    }

    public static void setUsername(String username) {
        JobContext jobContext = getOrInitContext();
        jobContext.setUsername(username);
    }

    public static Long getAppId() {
        JobContext jobContext = JobContextThreadLocal.get();
        Long appId = null;
        if (jobContext != null) {
            appId = jobContext.getAppId();
        }

        return appId;
    }

    public static void setAppId(Long appId) {
        JobContext jobContext = getOrInitContext();
        jobContext.setAppId(appId);
    }

    public static String getRequestId() {
        JobContext jobContext = JobContextThreadLocal.get();
        String requestId = null;
        if (jobContext != null) {
            requestId = jobContext.getRequestId();
        }

        return requestId;
    }

    public static void setRequestId(String requestId) {
        JobContext jobContext = getOrInitContext();
        jobContext.setRequestId(requestId);
    }

    public static String getUserLang() {
        JobContext jobContext = JobContextThreadLocal.get();
        String userLang = null;
        if (jobContext != null) {
            userLang = jobContext.getUserLang();
        }

        return userLang;
    }

    public static void setUserLang(String userLang) {
        JobContext jobContext = getOrInitContext();
        jobContext.setUserLang(userLang);
    }

    public static List<String> getDebugMessage() {
        JobContext jobContext = JobContextThreadLocal.get();
        List<String> debugMessage = null;
        if (jobContext != null) {
            debugMessage = jobContext.getDebugMessage();
        }
        if (debugMessage == null) {
            debugMessage = new ArrayList<>();
        }
        return debugMessage;
    }

    public static void addDebugMessage(String message) {
        JobContext jobContext = getOrInitContext();
        if (jobContext.getDebugMessage() == null) {
            jobContext.setDebugMessage(new ArrayList<>());
        }

        jobContext.getDebugMessage().add(message);
    }

    public static HttpServletRequest getRequest() {
        JobContext jobContext = JobContextThreadLocal.get();
        HttpServletRequest request = null;
        if (jobContext != null) {
            request = jobContext.getRequest();
        }
        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        JobContext jobContext = getOrInitContext();
        jobContext.setRequest(request);
    }

    public static HttpServletResponse getResponse() {
        JobContext jobContext = JobContextThreadLocal.get();
        HttpServletResponse response = null;
        if (jobContext != null) {
            response = jobContext.getResponse();
        }
        return response;
    }

    public static void setResponse(HttpServletResponse response) {
        JobContext jobContext = getOrInitContext();
        jobContext.setResponse(response);
    }

    public static ZoneId getTimeZone() {
        JobContext jobContext = JobContextThreadLocal.get();
        ZoneId timeZone = null;
        if (jobContext != null) {
            timeZone = jobContext.getTimeZone();
        }

        if (timeZone == null) {
            timeZone = ZoneOffset.ofHours(8);
            if (jobContext != null) {
                jobContext.setTimeZone(timeZone);
            }
        }
        return timeZone;
    }

    public static void setTimeZone(int hours) {
        JobContext jobContext = getOrInitContext();
        jobContext.setTimeZone(ZoneOffset.ofHours(hours));
    }

    public static void setTimeZone(ZoneId timeZone) {
        JobContext jobContext = getOrInitContext();
        jobContext.setTimeZone(timeZone);
    }

    public static void setAllowMigration(Boolean allowMigration) {
        JobContext jobContext = getOrInitContext();
        jobContext.setAllowMigration(allowMigration);
    }

    public static Boolean isAllowMigration() {
        JobContext jobContext = JobContextThreadLocal.get();
        if (jobContext != null) {
            return jobContext.getAllowMigration();
        }
        return false;
    }

    private static JobContext getOrInitContext() {
        JobContext jobContext = JobContextThreadLocal.get();
        if (jobContext == null) {
            jobContext = new JobContext();
            setContext(jobContext);
        }
        return jobContext;
    }

    public static String getHttpMetricName() {
        JobContext jobContext = JobContextThreadLocal.get();
        String httpMetricName = null;
        if (jobContext != null) {
            httpMetricName = jobContext.getHttpMetricName();
        }
        return httpMetricName;
    }

    public static void setHttpMetricName(String httpMetricName) {
        JobContext jobContext = getOrInitContext();
        jobContext.setHttpMetricName(httpMetricName);
    }

    public static AbstractList<Tag> getHttpMetricTags() {
        JobContext jobContext = getOrInitContext();
        if (jobContext.getHttpMetricTags() == null) {
            jobContext.setHttpMetricTags(new ArrayList<>());
        }
        return jobContext.getHttpMetricTags();
    }

    public static void addHttpMetricTag(Tag httpMetricTag) {
        AbstractList<Tag> httpMetricTags = getHttpMetricTags();
        httpMetricTags.add(httpMetricTag);
    }

    public static void clearHttpMetricTags() {
        JobContext jobContext = getOrInitContext();
        jobContext.setHttpMetricTags(null);
    }

}
