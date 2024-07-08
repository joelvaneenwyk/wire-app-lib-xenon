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

package com.wire.xenon.assets;

import com.waz.model.Messages;
import java.util.UUID;

public class Reaction implements IGeneric {

    private final UUID messageId = UUID.randomUUID();

    private final UUID msgId;
    private final String emoji;

    public Reaction(UUID msgId, String emoji) {
        this.msgId = msgId;
        this.emoji = emoji;
    }

    @Override
    public Messages.GenericMessage createGenericMsg() {
        Messages.Reaction.Builder reaction = Messages.Reaction.newBuilder()
            .setMessageId(msgId.toString())
            .setEmoji(emoji);

        return Messages.GenericMessage.newBuilder()
            .setMessageId(getMessageId().toString())
            .setReaction(reaction)
            .build();
    }

    @Override
    public UUID getMessageId() {
        return messageId;
    }
}
