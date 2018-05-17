/**
 * 
 */
package com.marf.evhunt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.marf.evhunt.model.Candidat;

/**
 * @author ahmeddammak
 *
 */
@Repository
public class CandidatRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Candidat findById(long id) {
		return jdbcTemplate.queryForObject("select * from candidat where id=?", new Object[] { id }, new BeanPropertyRowMapper<Candidat>(Candidat.class));
	}

	public List<Candidat> findAll() {
		String sql = "SELECT * FROM candidat";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Candidat>(Candidat.class));
	}

	public Candidat store(Candidat candidat, int idSource) {
		jdbcTemplate.update("insert into candidat (prenom,nom,mail,mobile,titre,competences,etudes,langues,controle_valide_rh,id_source) " + "values (?,?,?,?,?,?,?,?,?,?)", candidat.getPrenom(),
				candidat.getNom(), candidat.getMail(), candidat.getMobile(), candidat.getTitre(), candidat.getCompetences(), candidat.getEtudes(), candidat.getLangues(), "EN_ATTENTE", idSource);
		candidat.setId((long) (getIdMax() + 1));
		return candidat;
	}

	public Integer getIdMax() {
		Integer idMax = jdbcTemplate.queryForObject("select max(id) from candidat", new BeanPropertyRowMapper<Integer>(Integer.class));
		return idMax;
	}
}
