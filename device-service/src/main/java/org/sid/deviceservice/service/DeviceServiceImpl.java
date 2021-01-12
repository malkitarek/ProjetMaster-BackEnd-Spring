package org.sid.deviceservice.service;

import org.sid.deviceservice.dao.CapteurRepository;
import org.sid.deviceservice.dao.DeviceRepository;
import org.sid.deviceservice.entities.Capteur;
import org.sid.deviceservice.entities.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    CapteurRepository capteurRepository;


}
