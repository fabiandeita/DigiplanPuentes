package dao;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Documento entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see dao.Documento
 * @author MyEclipse Persistence Tools
 */
public class DocumentoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DocumentoDAO.class);
	// property constants
	public static final String KM_INICIAL = "kmInicial";
	public static final String KM_FINAL = "kmFinal";
	public static final String ARCHIVO = "archivo";
	public static final String OBSERVACIONES = "observaciones";
	public static final String ACTIVO = "activo";

	public void save(Documento transientInstance) {
		log.debug("saving Documento instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Documento persistentInstance) {
		log.debug("deleting Documento instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List findByQuery(String query) {
		log.debug("finding all Documento instances");
		try {
			String queryString = "from Documento as model " + query;
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Documento findById(java.lang.Integer id) {
		log.debug("getting Documento instance with id: " + id);
		try {
			Documento instance = (Documento) getSession().get("dao.Documento",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Documento instance) {
		log.debug("finding Documento instance by example");
		try {
			List results = getSession().createCriteria("dao.Documento")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Documento instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Documento as model where model."
					+ propertyName + "= ? and model.activo = 1  and model.activo = 1 " ;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByQuerySmart(String query) {
		try {
			String queryString = "from Documento as documento where documento.activo = 1 " + query + "" ;
			//System.out.println(queryString);
			Query queryObject = getSession().createQuery(queryString);			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByKmInicial(Object kmInicial) {
		return findByProperty(KM_INICIAL, kmInicial);
	}

	public List findByKmFinal(Object kmFinal) {
		return findByProperty(KM_FINAL, kmFinal);
	}

	public List findByArchivo(Object archivo) {
		return findByProperty(ARCHIVO, archivo);
	}

	public List findByObservaciones(Object observaciones) {
		return findByProperty(OBSERVACIONES, observaciones);
	}

	public List findByActivo(Object activo) {
		return findByProperty(ACTIVO, activo);
	}

	public List findAll() {
		log.debug("finding all Documento instances");
		try {
			String queryString = "from Documento";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllActive() {
		log.debug("finding all Documento instances");
		try {
			String queryString = "from Documento where activo = 1";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Documento merge(Documento detachedInstance) {
		log.debug("merging Documento instance");
		try {
			Documento result = (Documento) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Documento instance) {
		log.debug("attaching dirty Documento instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Documento instance) {
		log.debug("attaching clean Documento instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
    public List findBynoProyecto(String query) {
		
		try {
			String queryString = "select distinct(documento.proyecto.numProy) from Documento documento where documento.activo =  1  " + query ;
			System.out.println(queryString);
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    

}