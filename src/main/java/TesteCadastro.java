import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class TesteCadastro {
	
	private WebDriver driver;
	private CampoTreinamentoPage page;
	
	@Before
	public void inicializa() {
		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1200, 765));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		new DSL(driver);
		page = new CampoTreinamentoPage(driver);
	}
	
	@After
	public void finaliza() {
		driver.close();
	}

	@Test
	public void CadastroSucesso() {
		page.setNome("Sávio");
		page.setSobrenome("Borges");
		page.setSexoMasculino();
		page.setComidaPizza();
		page.setEscolaridade("Superior");
		page.setEsporte("Natacao");
		
		page.cadastrar();
		
		//Verificações
		Assert.assertTrue(page.obterResultadoCadastro().startsWith("Cadastrado!"));
		//Assert.assertEquals("Cadastrado!", driver.findElement(By.cssSelector("#resultado span")).getText());
		
		Assert.assertTrue(page.obterNomeCadastro().endsWith("Sávio"));
		Assert.assertEquals("Sobrenome: Borges", page.obterSobrenomeCadastro());
		Assert.assertEquals("Sexo: Masculino", page.obterSexoCadastro());
		Assert.assertEquals("Comida: Pizza", page.obterComidaCadastro());
		Assert.assertEquals("Escolaridade: superior", page.obterEscolaridadeCadastro());
		Assert.assertEquals("Esportes: Natacao", page.obterEsporteCadastro());	
	}
	
	@Test
	public void ValidarNome() {
		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Nome eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarSobrenome() {
		page.setNome("Sávio");
		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sobrenome eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarSexo() {
		page.setNome("Sávio");
		page.setSobrenome("Borges");
		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sexo eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarComidaVegetariana() {
		page.setNome("Sávio");
		page.setSobrenome("Borges");
		page.setSexoMasculino();
		page.setComidaCarne();
		page.setComidaVegetariana();
		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarEsporte() {
		page.setNome("Sávio");
		page.setSobrenome("Borges");
		page.setSexoMasculino();
		page.setComidaCarne();
		page.setEsporte("Natacao", "O que eh esporte?");
		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Voce faz esporte ou nao?", alert.getText());
		
		alert.accept();
	}
}
