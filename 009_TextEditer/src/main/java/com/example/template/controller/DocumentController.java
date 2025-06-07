package com.example.template.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	private static final String N_MSG_001 = "ファイルが保存されました：";
	private static final String N_MSG_002 = "ファイルが削除されました：";
	private static final String E_MSG_001 = "ファイルが保存に失敗しました：";
	private static final String E_MSG_002 = "ファイルが見つかりません。";
	private static final String E_MSG_003 = "ファイルの削除に失敗しました：";
	private static final String DOCUMENT_DIR = "src/main/resources/static/documents/";

	/**
	 * ファイルリスト取得
	 */
	@GetMapping("/list")
	public ResponseEntity<List<String>> getFileList() {
		try {
			List<String> fileList = Files.list(Paths.get(DOCUMENT_DIR))
					.filter(Files::isRegularFile)
					.map(Path::getFileName)
					.map(Path::toString)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(fileList, HttpStatus.OK);
		} catch(IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * ファイルの内容取得
	 */
	@GetMapping(value = "/{fileName}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getFileContent(@PathVariable String fileName) {
		Path filePath = Paths.get(DOCUMENT_DIR, fileName);
		
		try {
			if(!Files.exists(filePath)) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
			return new ResponseEntity<>(content, HttpStatus.OK);
		} catch(IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * ファイルの保存
	 */
	@PostMapping(value = "/{fileName}", consumes = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> saveFile(@PathVariable String fileName, @RequestBody String content) {
		
		// 末尾に.htmlがなければ.htmlを追加する
		if(!fileName.matches(".*\\.(html|txt)$")) {
			fileName += ".html";
		}
		
		Path filePath = Paths.get(DOCUMENT_DIR, fileName);
		try {
			Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
			return new ResponseEntity<>(N_MSG_001 + fileName, HttpStatus.OK);
		} catch(IOException e) {
			System.out.println(E_MSG_001 + e.getMessage());
			return new ResponseEntity<>(E_MSG_001 + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * ファイルの削除
	 */
	@DeleteMapping(value = "/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
		Path filePath = Paths.get(DOCUMENT_DIR, fileName);
		try {
			if(!Files.exists(filePath)) {
				return new ResponseEntity<>(E_MSG_002, HttpStatus.NOT_FOUND);
			}
			
			Files.delete(filePath);
			return new ResponseEntity<>(N_MSG_002 + fileName, HttpStatus.OK);
		} catch(IOException e) {
			System.out.println(E_MSG_003 + e.getMessage());
			return new ResponseEntity<>(E_MSG_003 + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
}
