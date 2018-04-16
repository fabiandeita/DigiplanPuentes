package dao;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tipodocumento entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see dao.Tipodocumento
 * @author MyEclipse Persistence Tools
 */

public class TipodocumentoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TipodocumentoDAO.class);
	// property constants
	public static final String NOMBRE = "nombre";

	public void save(Tipodocumento transientInstance) {
		log.debug("saving Tipodocumento instance");
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
			String queryString = "select distinct(documento.tipodocumento) from Documento documento where documento.tipodocumento.nombre like '%"+coincidence+"%'   and documento.activo = 1  order by documento.tipodocumento.nombre";
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
			String queryString = "select distinct(documento.tipodocumento) from Documento documento where documento.activo = 1  " + query + "  order by  documento.tipodocumento.nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void delete(Tipodocumento persistentInstance) {
		log.debug("deleting Tipodocumento instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tipodocumento findById(java.lang.Integer id) {
		log.debug("getting Tipodocumento instance with id: " + id);
		try {
			Tipodocumento instance = (Tipodocumento) getSession().get(
					"dao.Tipodocumento", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tipodocumento instance) {
		log.debug("finding Tipodocumento instance by example");
		try {
			List results = getSession().createCriteria("dao.Tipodocumento")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List findTDByTramo(String field, Object value) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.tipodocumento) from Documento documento where documento." + field + " = ?  order by documento.tipodocumento.nombre";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findTDByTramo(Integer proyecto, int tramo) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.tipodocumento) from Documento documento where documento.proyecto.idProyecto ="+proyecto+"  and documento.tramo.idTramo = ? order by documento.tipodocumento.nombre";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, tramo);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Tipodocumento instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Tipodocumento as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNombre(Object nombre) {
		return findByProperty(NOMBRE, nombre);
	}

	public List findAll() {
		log.debug("finding all Tipodocumento instances");
		try {
			String queryString = "from Tipodocumento as model order by model.nombre asc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tipodocumento merge(Tipodocumento detachedInstance) {
		log.debug("merging Tipodocumento instance");
		try {
			Tipodocumento result = (Tipodocumento) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tipodocumento instance) {
		log.debug("attaching dirty Tipodocumento instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tipodocumento instance) {
		log.debug("attaching clean Tipodocumento instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}