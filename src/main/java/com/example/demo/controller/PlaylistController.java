package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		
		//updating playlist table
		playlistService.addPlaylist(playlist);
		
		//update song table
		List<Song> songList = playlist.getSongs();
		for(Song s : songList) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
			//update song object in db
			
		}
		return "adminHome";
	}
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylists = playlistService.fetchAllSongs();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylist";
	}
}
