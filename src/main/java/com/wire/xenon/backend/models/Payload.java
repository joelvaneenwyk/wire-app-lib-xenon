//
// Wire
// Copyright (C) 2016 Wire Swiss GmbH
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see http://www.gnu.org/licenses/.
//

package com.wire.xenon.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    @JsonProperty
    @NotNull
    public String type;

    @JsonProperty("qualified_conversation")
    public Qualified conversation;

    @JsonProperty("qualified_from")
    public Qualified from;

    @JsonProperty
    public String time;

    @JsonProperty
    public Data data;

    @JsonProperty
    public UUID team;

    // User Mode
    @JsonProperty
    public Connection connection;

    @JsonProperty
    public User user;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty
        @NotNull
        public String sender;

        @JsonProperty
        @NotNull
        public String recipient;

        @JsonProperty
        public String text;

        @JsonProperty("user_ids")
        public List<UUID> userIds;

        @JsonProperty
        public String name;

        // User Mode
        @JsonProperty
        public String id;

        @JsonProperty
        public String key;

        @JsonProperty
        public UUID user;

        @JsonProperty
        public UUID creator;

        @JsonProperty
        public Members members;
    }

    // User Mode
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Connection {

        @JsonProperty
        public String status;

        @JsonProperty
        public UUID from;

        @JsonProperty
        public UUID to;

        @JsonProperty("conversation")
        public UUID convId;
    }

    // User Mode
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {

        @JsonProperty
        public UUID id;

        @JsonProperty
        public String name;

        @JsonProperty("accent_id")
        public int accent;

        @JsonProperty
        public String handle;

        @JsonProperty
        public String email;
    }

    // User Mode
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Members {

        @JsonProperty
        public List<Member> others;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Qualified {

        public Qualified(UUID id, String domain) {
            this.id = id;
            this.domain = domain;
        }

        public Qualified() {}

        @JsonProperty
        @NotNull
        public UUID id;

        @JsonProperty
        @NotNull
        public String domain;
    }
}
