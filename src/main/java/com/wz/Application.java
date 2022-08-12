package com.wz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.exception.InvalidInputException;
import com.wz.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class Application {
    public static void main(String[] args) {
        String operationName = args[0];
        String inputFileName = args[1];
        String outputFileName = args[2];

        try(FileReader reader = new FileReader(inputFileName);
            FileWriter writer = new FileWriter(outputFileName);){
            switch (operationName){
                case "search":
                    break;
                case "stat":
                    break;
                default: throw new InvalidInputException("You've specified an unsupported operation");
            }
        }
        catch (InvalidInputException inputException){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))){
                ObjectMapper mapper = new ObjectMapper();
                writer.write(mapper.writeValueAsString(new ErrorMessage("error", inputException.getMessage())));
                writer.flush();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
