package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tipoestructura entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see dao.Tipoestructura
 * @author MyEclipse Persistence Tools
 */
public class TipoestructuraDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TipoestructuraDAO.class);
	// property constants
	public static final String NOMBRE = "nombre";

	public void save(Tipoestructura transientInstance) {
		log.debug("saving Tipoestructura instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tipoestructura persistentInstance) {
		log.debug("deleting Tipoestructura instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tipoestructura findById(java.lang.Integer id) {
		log.debug("getting Tipoestructura instance with id: " + id);
		try {
			Tipoestructura instance = (Tipoestructura) getSession().get(
					"dao.Tipoestructura", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tipoestructura instance) {
		log.debug("finding Tipoestructura instance by example");
		try {
			List results = getSession().createCriteria("dao.Tipoestructura")
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
		log.debug("finding Tipoestructura instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Tipoestructura as model where model."
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
		log.debug("finding all Tipoestructura instances");
		try {
			String queryString = "from Tipoestructura";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tipoestructura merge(Tipoestructura detachedInstance) {
		log.debug("merging Tipoestructura instance");
		try {
			Tipoestructura result = (Tipoestructura) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tipoestructura instance) {
		log.debug("attaching dirty Tipoestructura instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tipoestructura instance) {
		log.debug("attaching clean Tipoestructura instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
    public List findByTramoProyectofindByEdoProTraTipEst(int idEstado, int idProyecto, int idTramo, int idTipodoc) {
		
		try {
			String queryString = "select distinct(documento.tipoEstructura) from Documento documento where documento.estado.idEstado = ? and documento.proyecto.idProyecto = "+idProyecto+"  and documento.tramo.idTramo = ? and documento.tipodocumento.idTipoDocumento = ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, idEstado);
			queryObject.setParameter(1, idTramo);
			queryObject.setParameter(2, idTipodoc);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    
    public List findByTramoProyecto(Object value, Object value2) {
		
 		try {
 			String queryString = "select distinct(documento.tipoEstructura) from Documento documento where documento.tramo = ? and documento.proyecto = ? ";
 			Query queryObject = getSession().createQuery(queryString);
 			queryObject.setParameter(0, value);
 			queryObject.setParameter(1, value2);
 			return queryObject.list();
 		} catch (RuntimeException re) {
 			log.error("find by property name failed", re);
 			throw re;
 		}
 	}
    
    public List findByCoincidenceQuery(String query) {
		log.debug("finding all Proyecto instances");
		try {
			String queryString = "select distinct(documento.tipoEstructura) from Documento documento where documento.activo = 1  " + query + "  order by  documento.tipoEstructura.nombre";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
     
}