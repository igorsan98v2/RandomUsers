package com.ygs.netronic.database.samples;

import com.ygs.netronic.interfaces.mappers.UserRowSampleMapper;
import com.ygs.netronic.models.ui.UserRowModel;

public class UserRowSample implements UserRowSampleMapper {
    public long id;
    public String firstName;
    public String lastName;
    public String thumbnailUrl;

    @Override
    public UserRowModel mapToUserRowModel() {
        return new UserRowModel.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserId(id)
                .setThumbnailUrl(thumbnailUrl)
                .create();
    }
}
