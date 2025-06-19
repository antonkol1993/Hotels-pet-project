package com.hotel.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface IHistogramController {

    @Operation(
            summary = "Get histogram by parameter",
            description = "Returns distribution of hotels by the specified parameter (e.g., city, brand, country)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histogram of values",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{param}")
    ResponseEntity<?> getHistogram(
            @Parameter(description = "Group by field: brand, city, country, amenities")
            @PathVariable("param") String param);
}
