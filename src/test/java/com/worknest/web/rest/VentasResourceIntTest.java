package com.worknest.web.rest;

import com.worknest.PruebaAzielV2App;

import com.worknest.domain.Ventas;
import com.worknest.repository.VentasRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VentasResource REST controller.
 *
 * @see VentasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PruebaAzielV2App.class)
public class VentasResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TOTALPAGAR = 1F;
    private static final Float UPDATED_TOTALPAGAR = 2F;

    private static final Float DEFAULT_GANANCIA = 1F;
    private static final Float UPDATED_GANANCIA = 2F;

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVentasMockMvc;

    private Ventas ventas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VentasResource ventasResource = new VentasResource(ventasRepository);
        this.restVentasMockMvc = MockMvcBuilders.standaloneSetup(ventasResource)
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
    public static Ventas createEntity(EntityManager em) {
        Ventas ventas = new Ventas()
            .fecha(DEFAULT_FECHA)
            .totalpagar(DEFAULT_TOTALPAGAR)
            .ganancia(DEFAULT_GANANCIA);
        return ventas;
    }

    @Before
    public void initTest() {
        ventas = createEntity(em);
    }

    @Test
    @Transactional
    public void createVentas() throws Exception {
        int databaseSizeBeforeCreate = ventasRepository.findAll().size();

        // Create the Ventas
        restVentasMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventas)))
            .andExpect(status().isCreated());

        // Validate the Ventas in the database
        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeCreate + 1);
        Ventas testVentas = ventasList.get(ventasList.size() - 1);
        assertThat(testVentas.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testVentas.getTotalpagar()).isEqualTo(DEFAULT_TOTALPAGAR);
        assertThat(testVentas.getGanancia()).isEqualTo(DEFAULT_GANANCIA);
    }

    @Test
    @Transactional
    public void createVentasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventasRepository.findAll().size();

        // Create the Ventas with an existing ID
        ventas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentasMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventas)))
            .andExpect(status().isBadRequest());

        // Validate the Ventas in the database
        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventasRepository.findAll().size();
        // set the field null
        ventas.setFecha(null);

        // Create the Ventas, which fails.

        restVentasMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventas)))
            .andExpect(status().isBadRequest());

        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalpagarIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventasRepository.findAll().size();
        // set the field null
        ventas.setTotalpagar(null);

        // Create the Ventas, which fails.

        restVentasMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventas)))
            .andExpect(status().isBadRequest());

        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGananciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventasRepository.findAll().size();
        // set the field null
        ventas.setGanancia(null);

        // Create the Ventas, which fails.

        restVentasMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventas)))
            .andExpect(status().isBadRequest());

        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVentas() throws Exception {
        // Initialize the database
        ventasRepository.saveAndFlush(ventas);

        // Get all the ventasList
        restVentasMockMvc.perform(get("/api/ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventas.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].totalpagar").value(hasItem(DEFAULT_TOTALPAGAR.doubleValue())))
            .andExpect(jsonPath("$.[*].ganancia").value(hasItem(DEFAULT_GANANCIA.doubleValue())));
    }

    @Test
    @Transactional
    public void getVentas() throws Exception {
        // Initialize the database
        ventasRepository.saveAndFlush(ventas);

        // Get the ventas
        restVentasMockMvc.perform(get("/api/ventas/{id}", ventas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ventas.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.totalpagar").value(DEFAULT_TOTALPAGAR.doubleValue()))
            .andExpect(jsonPath("$.ganancia").value(DEFAULT_GANANCIA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVentas() throws Exception {
        // Get the ventas
        restVentasMockMvc.perform(get("/api/ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVentas() throws Exception {
        // Initialize the database
        ventasRepository.saveAndFlush(ventas);
        int databaseSizeBeforeUpdate = ventasRepository.findAll().size();

        // Update the ventas
        Ventas updatedVentas = ventasRepository.findOne(ventas.getId());
        updatedVentas
            .fecha(UPDATED_FECHA)
            .totalpagar(UPDATED_TOTALPAGAR)
            .ganancia(UPDATED_GANANCIA);

        restVentasMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVentas)))
            .andExpect(status().isOk());

        // Validate the Ventas in the database
        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeUpdate);
        Ventas testVentas = ventasList.get(ventasList.size() - 1);
        assertThat(testVentas.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testVentas.getTotalpagar()).isEqualTo(UPDATED_TOTALPAGAR);
        assertThat(testVentas.getGanancia()).isEqualTo(UPDATED_GANANCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingVentas() throws Exception {
        int databaseSizeBeforeUpdate = ventasRepository.findAll().size();

        // Create the Ventas

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVentasMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventas)))
            .andExpect(status().isCreated());

        // Validate the Ventas in the database
        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVentas() throws Exception {
        // Initialize the database
        ventasRepository.saveAndFlush(ventas);
        int databaseSizeBeforeDelete = ventasRepository.findAll().size();

        // Get the ventas
        restVentasMockMvc.perform(delete("/api/ventas/{id}", ventas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ventas> ventasList = ventasRepository.findAll();
        assertThat(ventasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ventas.class);
        Ventas ventas1 = new Ventas();
        ventas1.setId(1L);
        Ventas ventas2 = new Ventas();
        ventas2.setId(ventas1.getId());
        assertThat(ventas1).isEqualTo(ventas2);
        ventas2.setId(2L);
        assertThat(ventas1).isNotEqualTo(ventas2);
        ventas1.setId(null);
        assertThat(ventas1).isNotEqualTo(ventas2);
    }
}
