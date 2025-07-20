/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dto.Docente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import util.AESUtil;

/**
 *
 * @author harol
 */
public class DocenteJpaController implements Serializable {

    public DocenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Prac_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DocenteJpaController() {
    }

    public void create(Docente docente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docente docente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            docente = em.merge(docente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = docente.getCodiDoce();
                if (findDocente(id) == null) {
                    throw new NonexistentEntityException("The docente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente;
            try {
                docente = em.getReference(Docente.class, id);
                docente.getCodiDoce();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docente with id " + id + " no longer exists.", enfe);
            }
            em.remove(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docente> findDocenteEntities() {
        return findDocenteEntities(true, -1, -1);
    }

    public List<Docente> findDocenteEntities(int maxResults, int firstResult) {
        return findDocenteEntities(false, maxResults, firstResult);
    }

    private List<Docente> findDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Docente findDocente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docente> rt = cq.from(Docente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Docente validarUsuario(Docente u) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Docente.validar", Docente.class);
            q.setParameter("dniDoce", u.getDniDoce());
            q.setParameter("claveDoce", u.getClaveDoce());
            return (Docente) q.getSingleResult();
        } catch (Exception ex) {
            String mensaje = ex.getMessage();
            return null;
        } finally {
            em.close();
        }
    }

    public String cambiarClave(Docente u, String nuevaClave) {
        EntityManager em = getEntityManager();
        try {
            // Cifra la clave ingresada para validación
            String claveCifradaIngresada = AESUtil.cifrar(u.getClaveDoce(), "claveaesclaveaes");

            Docente usuario = validarUsuario(new Docente(u.getDniDoce(), claveCifradaIngresada));

            if (usuario != null) {
                // Cifra la nueva clave
                String nuevaClaveCifrada = AESUtil.cifrar(nuevaClave, "claveaesclaveaes");
                usuario.setClaveDoce(nuevaClaveCifrada);

                edit(usuario); // Actualiza en la BD
                return "Clave cambiada";
            } else {
                return "Usuario no encontrado o clave actual incorrecta";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error al cambiar la clave";
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        DocenteJpaController vurDAO = new DocenteJpaController();
        String contra_plana = "1234";
        String contracifrada = AESUtil.cifrar(contra_plana, "1234567890123456");
        System.out.println("contra cifrada: " + contracifrada);
        String contradescifrada = AESUtil.descifrar(contracifrada, "1234567890123456");
        System.out.println("contra descifrada: " + contradescifrada);

        Docente vur = vurDAO.validarUsuario(new Docente("73519331", "PwVl7PYPDavdLSqeb3FQBA=="));

        System.out.println(vur);
        if (vur != null) {
            System.out.println("PERSONA ENCONTRADA");
        } else {
            System.out.println("PERSONA NO ENCONTRADA");
        }
    }

}
