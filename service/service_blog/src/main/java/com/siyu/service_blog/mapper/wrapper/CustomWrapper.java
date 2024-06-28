package com.siyu.service_blog.mapper.wrapper;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomWrapper {
    private List<String> cols;
    private String mode;
    private String prop;
    private List<String> props;
}
