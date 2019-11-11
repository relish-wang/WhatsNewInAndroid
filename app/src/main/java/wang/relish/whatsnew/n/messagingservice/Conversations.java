/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package wang.relish.whatsnew.n.messagingservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 表示未读对话和信息。在真实的应用程序里，
 * 应该使用内容提供者（content provider）获取到真实的未读信息并显示给用户
 */
public class Conversations {

    private static final String[] MESSAGES = new String[]{
            "你在家嘛?",
            "请回个电话给我。",
            "嗨，在吗?",
            "不要忘记在你回家路上给我带点牛奶。",
            "项目完成了嘛?",
            "你完成了Message App了吗?"
    };

    /**
     * 发送者的信息
     */
    private static final String[] PARTICIPANTS = new String[]{
            "张三",
            "李四",
            "王五",
            "老宋"
    };

    static class Conversation {

        private final int conversationId;

        private final String participantName;

        /**
         * 一个给出的对话可以拥有一条或多条信息。
         * 信息是从最晚（最新）到最早（最旧）排序的。
         */
        private final List<String> messages;

        private final long timestamp;


        public Conversation(int conversationId, String participantName,
                            List<String> messages) {
            this.conversationId = conversationId;
            this.participantName = participantName;
            this.messages = messages == null ? Collections.<String>emptyList() : messages;
            this.timestamp = System.currentTimeMillis();
        }

        public int getConversationId() {
            return conversationId;
        }

        public String getParticipantName() {
            return participantName;
        }

        public List<String> getMessages() {
            return messages;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String toString() {
            return "[对话: 对话ID=" + conversationId +
                    ", 参与者名=" + participantName +
                    ", 信息=" + messages +
                    ", 时间戳=" + timestamp + "]";
        }

    }

    private Conversations() {
    }

    public static Conversation[] getUnreadConversations(int howManyConversations,
                                                        int messagesPerConversation) {
        Conversation[] conversations = new Conversation[howManyConversations];
        for (int i = 0; i < howManyConversations; i++) {
            conversations[i] = new Conversation(
                    (int) (System.currentTimeMillis() % Integer.MAX_VALUE) - i,
                    name(), makeMessages(messagesPerConversation));
        }
        return conversations;
    }

    private static List<String> makeMessages(int messagesPerConversation) {
        int maxLen = MESSAGES.length;
        List<String> messages = new ArrayList<>(messagesPerConversation);
        for (int i = 0; i < messagesPerConversation; i++) {
            messages.add(MESSAGES[ThreadLocalRandom.current().nextInt(0, maxLen)]);
        }
        return messages;
    }

    private static String name() {
        return PARTICIPANTS[ThreadLocalRandom.current().nextInt(0, PARTICIPANTS.length)];
    }
}
