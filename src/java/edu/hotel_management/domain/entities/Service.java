package edu.hotel_management.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@Getter
public class Service {
    private  int serviceId;
    private  String serviceName;
    private String serviceType;
    private  double price;
}
