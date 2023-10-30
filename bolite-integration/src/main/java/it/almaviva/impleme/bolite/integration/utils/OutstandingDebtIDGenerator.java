package it.almaviva.impleme.bolite.integration.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class OutstandingDebtIDGenerator implements IdentifierGenerator  {

	
    public static final String generatorName = "outstandingDebtGenerator";
    
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		 return "od-"+UUID.randomUUID().toString();
	}

}
