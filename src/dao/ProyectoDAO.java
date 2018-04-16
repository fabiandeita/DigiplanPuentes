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
 * Proyecto entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see dao.Proyecto
 * @author MyEclipse Persistence Tools
 */

public class ProyectoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ProyectoDAO.class);
	// property constants
	public static final String NOMBRE = "nombre";
	public static final String NUMPROY = "numProy";

	public void save(Proyecto transientInstance) {
		log.debug("saving Proyecto instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Proyecto persistentInstance) {
		log.debug("deleting Proyecto instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Proyecto findById(java.lang.Integer id) {
		log.debug("getting Proyecto instance with id: " + id);
		try {
			Proyecto instance = (Proyecto) getSession().get("dao.Proyecto", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Proyecto instance) {
		log.debug("finding Proyecto instance by example");
		try {
			List results = getSession().createCriteria("dao.Proyecto")
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
		log.debug("finding Proyecto instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Proyecto as model where model."
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
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "from Proyecto order by nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findByEntidad(Object value) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.proyecto) from Documento documento where documento.estado = ? and documento.activo =  1 order by documento.proyecto.nombre";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findByCoincidence(String coincidence) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.proyecto) from Documento documento where documento.proyecto.nombre like '%"+coincidence+"%' and documento.activo =  1 order by documento.proyecto.nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Proyecto findByNumeroProyecto(String numProy) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select proyecto from Proyecto proyecto where proyecto.numProy like '"+numProy+"' ";
			Query queryObject = getSession().createQuery(queryString);
			List list = queryObject.list();
			if (list.isEmpty())
				return null;
			else
				return (Proyecto)list.get(0);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findByCoincidenceQuery(String coincidence) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.proyecto) from Documento documento where documento.activo =  1  " + coincidence + " order by  documento.proyecto.nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findByCoincidencia(String coincidencia) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(numProy) from Proyecto proy where proy.numProy like '%" + coincidencia + "%' order by  proyecto.numProy";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Proyecto merge(Proyecto detachedInstance) {
		log.debug("merging Proyecto instance");
		try {
			Proyecto result = (Proyecto) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Proyecto instance) {
		log.debug("attaching dirty Proyecto instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Proyecto instance) {
		log.debug("attaching clean Proyecto instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}