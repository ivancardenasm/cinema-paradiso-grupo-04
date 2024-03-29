package com.cinema.cinemaparadiso.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.cinemaparadiso.model.Artist;
import com.cinema.cinemaparadiso.model.Project;
import com.cinema.cinemaparadiso.model.User;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Integer> {

	@Query("SELECT artist FROM Artist artist WHERE artist.user.username = :username")
	public Artist findByUsername(@Param("username") String username);

	@Query("SELECT project FROM Project project INNER JOIN Rel_projects_artists rel_projects_artists ON project.id = rel_projects_artists.project_id AND rel_projects_artists.artist_id = :artistId")
	public List<Project> findMyProjects(@Param("artistId") Integer artistId);

	// COMPROBAR ARTISTA LOGUEADO

	@Query("SELECT artist FROM Artist artist WHERE artist.user.username = :username")
	public Optional<Artist> findByUserUsername(String username);

	@Query("SELECT artist FROM Artist artist WHERE artist.pro = 1")
	public List<Artist> findProArtists();

	@Query("SELECT artist FROM Artist artist WHERE artist.pro = 0")
	public List<Artist> findNoProArtists();

	@Query("SELECT artist.projectsHistory FROM Artist artist WHERE artist.id =: id")
	public List<Project> findProjectsHistory(@Param("id") Integer id);

	@Query("SELECT user FROM User user WHERE user.username = :username")
	public Optional<User> findUserByArtistUsername(String username);

	@Query(value = "SELECT artist.left_projects FROM Artists artist WHERE artist.id = :artistId", nativeQuery = true)
	public Integer findProjectsLeft(Integer artistId);

	@Transactional
	@Modifying
	@Query("UPDATE Artist artist SET artist.leftProjects = artist.leftProjects + :addingAmount WHERE artist.id = :artistID")
	public void incrementLeftProjectsOfArtist(@Param("artistID") Integer artistID,
			@Param("addingAmount") Integer addingAmount);

	@Transactional
	@Modifying
	@Query("UPDATE Artist artist SET artist.pro = TRUE WHERE artist.id = :artistID")
	public void makePro(Integer artistID);
}
