package com.worknest.web.rest;

import com.worknest.PruebaAzielV2App;

import com.worknest.domain.Historial;
import com.worknest.repository.HistorialRepository;
import com.worknest.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HistorialResource REST controller.
 *
 * @see HistorialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PruebaAzielV2App.class)
public class HistorialResourceIntTest {

    private static final Float DEFAULT_PRECIOACTUAL = 1F;
    private static final Float UPDATED_PRECIOACTUAL = 2F;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Float DEFAULT_TOTALPRODUCTO = 1F;
    private static final Float UPDATED_TOTALPRODUCTO = 2F;

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistorialMockMvc;

    private Historial historial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistorialResource historialResource = new HistorialResource(historialRepository);
        this.restHistorialMockMvc = MockMvcBuilders.standaloneSetup(historialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historial createEntity(EntityManager em) {
        Historial historial = new Historial()
            .precioactual(DEFAULT_PRECIOACTUAL)
            .cantidad(DEFAULT_CANTIDAD)
            .totalproducto(DEFAULT_TOTALPRODUCTO);
        return historial;
    }

    @Before
    public void initTest() {
        historial = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistorial() throws Exception {
        int databaseSizeBeforeCreate = historialRepository.findAll().size();

        // Create the Historial
        restHistorialMockMvc.perform(post("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historial)))
            .andExpect(status().isCreated());

        // Validate the Historial in the database
        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeCreate + 1);
        Historial testHistorial = historialList.get(historialList.size() - 1);
        assertThat(testHistorial.getPrecioactual()).isEqualTo(DEFAULT_PRECIOACTUAL);
        assertThat(testHistorial.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testHistorial.getTotalproducto()).isEqualTo(DEFAULT_TOTALPRODUCTO);
    }

    @Test
    @Transactional
    public void createHistorialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historialRepository.findAll().size();

        // Create the Historial with an existing ID
        historial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorialMockMvc.perform(post("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historial)))
            .andExpect(status().isBadRequest());

        // Validate the Historial in the database
        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrecioactualIsRequired() throws Exception {
        int databaseSizeBeforeTest = historialRepository.findAll().size();
        // set the field null
        historial.setPrecioactual(null);

        // Create the Historial, which fails.

        restHistorialMockMvc.perform(post("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historial)))
            .andExpect(status().isBadRequest());

        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = historialRepository.findAll().size();
        // set the field null
        historial.setCantidad(null);

        // Create the Historial, which fails.

        restHistorialMockMvc.perform(post("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historial)))
            .andExpect(status().isBadRequest());

        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalproductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = historialRepository.findAll().size();
        // set the field null
        historial.setTotalproducto(null);

        // Create the Historial, which fails.

        restHistorialMockMvc.perform(post("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historial)))
            .andExpect(status().isBadRequest());

        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistorials() throws Exception {
        // Initialize the database
        historialRepository.saveAndFlush(historial);

        // Get all the historialList
        restHistorialMockMvc.perform(get("/api/historials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historial.getId().intValue())))
            .andExpect(jsonPath("$.[*].precioactual").value(hasItem(DEFAULT_PRECIOACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].totalproducto").value(hasItem(DEFAULT_TOTALPRODUCTO.doubleValue())));
    }

    @Test
    @Transactional
    public void getHistorial() throws Exception {
        // Initialize the database
        historialRepository.saveAndFlush(historial);

        // Get the historial
        restHistorialMockMvc.perform(get("/api/historials/{id}", historial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historial.getId().intValue()))
            .andExpect(jsonPath("$.precioactual").value(DEFAULT_PRECIOACTUAL.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.totalproducto").value(DEFAULT_TOTALPRODUCTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHistorial() throws Exception {
        // Get the historial
        restHistorialMockMvc.perform(get("/api/historials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistorial() throws Exception {
        // Initialize the database
        historialRepository.saveAndFlush(historial);
        int databaseSizeBeforeUpdate = historialRepository.findAll().size();

        // Update the historial
        Historial updatedHistorial = historialRepository.findOne(historial.getId());
        updatedHistorial
            .precioactual(UPDATED_PRECIOACTUAL)
            .cantidad(UPDATED_CANTIDAD)
            .totalproducto(UPDATED_TOTALPRODUCTO);

        restHistorialMockMvc.perform(put("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistorial)))
            .andExpect(status().isOk());

        // Validate the Historial in the database
        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeUpdate);
        Historial testHistorial = historialList.get(historialList.size() - 1);
        assertThat(testHistorial.getPrecioactual()).isEqualTo(UPDATED_PRECIOACTUAL);
        assertThat(testHistorial.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testHistorial.getTotalproducto()).isEqualTo(UPDATED_TOTALPRODUCTO);
    }

    @Test
    @Transactional
    public void updateNonExistingHistorial() throws Exception {
        int databaseSizeBeforeUpdate = historialRepository.findAll().size();

        // Create the Historial

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistorialMockMvc.perform(put("/api/historials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historial)))
            .andExpect(status().isCreated());

        // Validate the Historial in the database
        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHistorial() throws Exception {
        // Initialize the database
        historialRepository.saveAndFlush(historial);
        int databaseSizeBeforeDelete = historialRepository.findAll().size();

        // Get the historial
        restHistorialMockMvc.perform(delete("/api/historials/{id}", historial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Historial> historialList = historialRepository.findAll();
        assertThat(historialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historial.class);
        Historial historial1 = new Historial();
        historial1.setId(1L);
        Historial historial2 = new Historial();
        historial2.setId(historial1.getId());
        assertThat(historial1).isEqualTo(historial2);
        historial2.setId(2L);
        assertThat(historial1).isNotEqualTo(historial2);
        historial1.setId(null);
        assertThat(historial1).isNotEqualTo(historial2);
    }
}
