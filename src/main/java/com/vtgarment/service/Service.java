package com.vtgarment.service;

import org.slf4j.Logger;

import javax.annotation.Resource;
import java.io.Serializable;

public abstract class Service implements Serializable {
    @Resource protected Logger log;
}
