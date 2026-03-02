package ollamaStar.server.service;

import org.springframework.stereotype.Service;

@Service
public interface OllamaService {
    public String translate(String propmt);
}
