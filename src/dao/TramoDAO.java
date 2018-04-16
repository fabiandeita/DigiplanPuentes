package dao;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Tramo
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see dao.Tramo
 * @author MyEclipse Persistence Tools
 */

public class TramoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(TramoDAO.class);
	// property constants
	public static final String NOMBRE = "nombre";

	public void save(Tramo transientInstance) {
		log.debug("saving Tramo instance");
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
			String queryString = "select distinct(documento.tramo) from Documento documento where documento.tramo.nombre like '%"+coincidence+"%'  and documento.activo = 1 order by documento.tramo.nombre";
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
			String queryString = "select distinct(documento.tramo) from Documento documento where  documento.activo = 1  " + query + " order by documento.tramo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List orderABC(){
		log.debug("finding all Tramo instances alphabetically");
		try {
			String queryString = "from Tramo order by Tramo asc";
			Query queryObject = getSession().createQuery(queryString);
			log.debug("finding all alphabetically successful");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List find(int idEstado, String nombre){
		log.debug("finding all Tramo instances alphabetically");
		try {
			String queryString = "from Tramo as model where model.estado.idEstado = " + idEstado + " and model.nombre = '" + nombre + "' order by Tramo asc";
			Query queryObject = getSession().createQuery(queryString);
			log.debug("finding all alphabetically successful");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findByEstado(int idEstado){
		log.debug("finding all Tramo instances alphabetically");
		try {
			String queryString = "select distinct (tramo.carretera) from Tramo as tramo where idEstado=" + idEstado + " ";
			Query queryObject = getSession().createQuery(queryString);
			log.debug("finding all alphabetically successful");
			return queryObject.list();
		} catch (RuntimeException re) {
			System.out.println("ldaslkdjaslkj");
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findByIdEstado(int idEstado){
		log.debug("finding all Tramo instances alphabetically");
		try {
			String queryString = "from Tramo as tramo where tramo.estado.idEstado=" + idEstado + " ";
			Query queryObject = getSession().createQuery(queryString);
			log.debug("finding all alphabetically successful");
			return queryObject.list();
		} catch (RuntimeException re) {
			System.out.println("ldaslkdjaslkj");
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void delete(Tramo persistentInstance) {
		log.debug("deleting Tramo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tramo findById(java.lang.Integer id) {
		log.debug("getting Tramo instance with id: " + id);
		try {
			Tramo instance = (Tramo) getSession().get("dao.Tramo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List orderABC(int idEstado){
		log.debug("finding all Tramo instances alphabetically");
		try {
			String queryString = "from Tramo as model where model.estado.idEstado ='"+idEstado+"' order by model.nombre asc";
			Query queryObject = getSession().createQuery(queryString);
			log.debug("finding all alphabetically successful");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findByExample(Tramo instance) {
		log.debug("finding Tramo instance by example");
		try {
			List results = getSession().createCriteria("dao.Tramo")
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
		log.debug("finding Tramo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tramo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProyecto(int idEstado, Object value, int idCarretera) {
		
		try {
			
			String queryString = "select distinct(documento.tramo) from Documento documento where documento.estado.idEstado = ? and documento.proyecto = ? and documento.tramo.carretera.idCarretera = ? order by documento.tramo.nombre";
			System.out.println(queryString);
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, idEstado);
			queryObject.setParameter(1, value);
			queryObject.setParameter(2, idCarretera);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByCarretera(int idEstado, Object value) {
		
		try {
			String queryString = "select distinct(documento.tramo) from Documento documento where documento.estado.idEstado = ? and documento.proyecto = ? order by documento.tramo.nombre";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, idEstado);
			queryObject.setParameter(1, value);
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
		log.debug("finding all Tramo instances");
		try {
			String queryString = "from Tramo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	public List findPlanos(Tramo tramo) {
		log.debug("finding 10 planos from the Tramo");
		try {
			String queryString = "select archivo from Documento where tramo = ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setMaxResults(10);
			queryObject.setParameter(0, tramo);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tramo merge(Tramo detachedInstance) {
		log.debug("merging Tramo instance");
		try {
			Tramo result = (Tramo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tramo instance) {
		log.debug("attaching dirty Tramo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tramo instance) {
		log.debug("attaching clean Tramo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}