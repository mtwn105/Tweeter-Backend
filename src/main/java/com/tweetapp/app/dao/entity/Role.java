package com.tweetapp.app.dao.entity;

import com.tweetapp.app.model.ERole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
