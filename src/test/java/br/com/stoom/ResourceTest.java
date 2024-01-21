package br.com.stoom;

import org.junit.jupiter.api.Tag;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Tag("e2eTest")
public abstract class ResourceTest {

    @Autowired
    protected MockMvc mvc;


    protected MockHttpServletRequestBuilder getBuild(final String url) {
        return get(url)
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder postBuild(final String url) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON);
    }

}
