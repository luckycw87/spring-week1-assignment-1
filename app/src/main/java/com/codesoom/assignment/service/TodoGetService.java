package com.codesoom.assignment.service;

import com.codesoom.assignment.model.ResponseData;
import com.codesoom.assignment.model.Task;
import com.codesoom.assignment.repository.TaskRepository;
import com.codesoom.assignment.repository.TaskRepositoryImpl;
import com.codesoom.assignment.util.HttpConst;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class TodoGetService implements TodoService {
    private final TaskRepository taskRepository = TaskRepositoryImpl.getInstance();
    @Override
    public ResponseData processRequest(HttpExchange exchange, Task taskParam, String pathVariable) throws IOException {
        if (isAllSearchRequest(pathVariable)) {
            List<Task> taskList = taskRepository.findAll();
            return new ResponseData(HttpConst.HTTP_OK, convertToJSON(taskList));

        } else if (isBadRequest(pathVariable)) {
            return new ResponseData(HttpConst.HTTP_BAD_REQUEST, "");

        } else {
            Task task = taskRepository.findById(Long.parseLong(pathVariable));
            return new ResponseData(HttpConst.HTTP_OK, task != null ? convertToJSON(task) : "");

        }
    }

    private boolean isBadRequest(String pathVariable) {
        return pathVariable.isBlank();
    }

    private boolean isAllSearchRequest(String pathVariable) {
        return pathVariable == null;
    }
}