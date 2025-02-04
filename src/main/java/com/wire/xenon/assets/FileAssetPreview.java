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

public class FileAssetPreview implements IGeneric {

    private final String name;
    private final String mimeType;
    private final long size;
    private final UUID messageId;

    public FileAssetPreview(String name, String mimeType, long size, UUID messageId) {
        this.messageId = messageId;
        this.name = name;
        this.mimeType = mimeType;
        this.size = size;
    }

    @Override
    public Messages.GenericMessage createGenericMsg() {
        Messages.Asset.Original.Builder original = Messages.Asset.Original.newBuilder()
            .setSize(size)
            .setName(name)
            .setMimeType(mimeType);

        Messages.Asset.Builder asset = Messages.Asset.newBuilder().setOriginal(original);

        return Messages.GenericMessage.newBuilder().setMessageId(getMessageId().toString()).setAsset(asset).build();
    }

    public String getName() {
        return name;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public UUID getMessageId() {
        return messageId;
    }
}
