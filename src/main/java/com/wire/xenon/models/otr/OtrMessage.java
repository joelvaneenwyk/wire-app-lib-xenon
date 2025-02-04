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

package com.wire.xenon.models.otr;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class OtrMessage {

    @JsonProperty
    @NotNull
    private final String sender; //clientId of the sender

    @JsonProperty
    private final Recipients recipients;

    public OtrMessage(String clientId, Recipients recipients) {
        this.sender = clientId;
        this.recipients = recipients;
    }

    public void add(Recipients rec) {
        recipients.add(rec);
    }

    public String get(UUID userId, String clientId) {
        return recipients.get(userId, clientId);
    }

    public int size() {
        int count = 0;
        for (ClientCipher devs : recipients.values()) {
            count += devs.size();
        }
        return count;
    }

    public String getSender() {
        return sender;
    }
}
