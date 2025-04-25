package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todolist.entity.Status;
import com.example.todolist.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusServiceImp implements StatusService {
	
	private final StatusRepository statusRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Status> findAll() {
		
		List<Status> list = statusRepository.selectAll();

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Status findById(Integer statusId) {
		
		Status status = statusRepository.selectById(statusId);
		
		return status;
	}

}
