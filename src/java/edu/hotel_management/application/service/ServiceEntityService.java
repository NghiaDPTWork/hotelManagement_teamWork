package edu.hotel_management.application.service;

import edu.hotel_management.domain.dto.service.ServiceViewModel;
import edu.hotel_management.domain.entities.Service;
import edu.hotel_management.infrastructure.persistence.dao.ServiceDAO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class ServiceEntityService {
    private final ServiceDAO serviceDao;

    public ServiceEntityService(ServiceDAO serviceDao) {
        this.serviceDao = serviceDao;
    }

    // (method reference)
    public List<ServiceViewModel> getAllServices() {
        return serviceDao.findAll().stream()
                .map(ServiceViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public ServiceViewModel getServiceById(int id) {
        Service service = serviceDao.findById(id).orElse(null);
        return service != null ? ServiceViewModel.fromEntity(service) : null;
    }

}
