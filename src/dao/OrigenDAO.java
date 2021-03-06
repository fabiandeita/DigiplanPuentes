package dao;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Origen entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see dao.Origen
  * @author MyEclipse Persistence Tools 
 */

public class OrigenDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(OrigenDAO.class);
		//property constants
	public static final String NOMBRE = "nombre";



    
    public void save(Origen transientInstance) {
        log.debug("saving Origen instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    public List findByCoincidence(String coincidence) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.origen) from Documento documento where documento.origen.nombre like '%"+coincidence+"%'  and documento.activo = 1  order by documento.origen.nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
    
    public List findByCoincidenceQuery(String query) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.origen) from Documento documento where documento.activo = 1  " + query + "  order by  documento.origen.nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
    
	public void delete(Origen persistentInstance) {
        log.debug("deleting Origen instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Origen findById( java.lang.Integer id) {
        log.debug("getting Origen instance with id: " + id);
        try {
            Origen instance = (Origen) getSession()
                    .get("dao.Origen", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Origen instance) {
        log.debug("finding Origen instance by example");
        try {
            List results = getSession()
                    .createCriteria("dao.Origen")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }  
    

    
    public List findByTramoProyectofindByEdoProTraTipDoc(int idEstado, int idProyecto, int idTramo, int idTipoDoc, int idTipoEst) {
		
		try {
			String queryString = "select distinct(documento.origen) from Documento documento where documento.estado.idEstado = ? and documento.proyecto.idProyecto = "+idProyecto+"  and documento.tramo.idTramo = ? and documento.tipodocumento.idTipoDocumento = ?   and documento.tipoEstructura.idTipoEstructura = ? order by documento.origen.nombre";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, idEstado);
			queryObject.setParameter(1, idTramo);
			queryObject.setParameter(2, idTipoDoc);
			queryObject.setParameter(3, idTipoEst);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    
    public List findByTramoProyecto(Object value, Object value2) {
		
		try {
			String queryString = "select distinct(documento.origen) from Documento documento where documento.tramo = ? and documento.proyecto = ? order by documento.origen.nombre";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setParameter(1, value2);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Origen instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Origen as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByNombre(Object nombre
	) {
		return findByProperty(NOMBRE, nombre
		);
	}
	

	public List findAll() {
		log.debug("finding all Origen instances");
		try {
			String queryString = "from Origen as model order by model.nombre";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Origen merge(Origen detachedInstance) {
        log.debug("merging Origen instance");
        try {
            Origen result = (Origen) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Origen instance) {
        log.debug("attaching dirty Origen instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Origen instance) {
        log.debug("attaching clean Origen instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}