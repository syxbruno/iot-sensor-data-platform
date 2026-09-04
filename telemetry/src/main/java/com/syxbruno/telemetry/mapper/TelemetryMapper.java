package com.syxbruno.telemetry.mapper;

import com.syxbruno.telemetry.dto.request.SensorDataRequest;
import com.syxbruno.telemetry.model.Telemetry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelemetryMapper {

    Telemetry toTelemetry(SensorDataRequest data);
}
