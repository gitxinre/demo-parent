package com.ly.demo.sso.web.entity.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xinre
 */
@Builder
@Getter
@Setter
@ToString
public class ClientInfoDTO {

    private String clientUrl;
    private String jSessionId;

}
