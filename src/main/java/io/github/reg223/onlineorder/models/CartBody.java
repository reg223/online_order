package io.github.reg223.onlineorder.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CartBody(
        @JsonProperty("menu_id") Long menuId
) {
}
