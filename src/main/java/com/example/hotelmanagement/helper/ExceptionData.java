package com.example.hotelmanagement.helper;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.Message;

public record ExceptionData(String message, String status, String webRequest) {}