import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class DesafioCadastroComSucesso {
	
	private WebDriver driver;
	private DSL dsl;
	
	@Before
	public void inicializa() {
		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1200, 765));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
	}
	
	@After
	public void finaliza() {
		driver.close();
	}

	@Test
	public void CadastroSucesso() {
		dsl.escreve("elementosForm:nome", "Sávio");
		dsl.escreve("elementosForm:sobrenome", "Borges");
		dsl.clicarRadio("elementosForm:sexo:0");
		dsl.clicarRadio("elementosForm:comidaFavorita:2");
		
		dsl.selecionarCombo("elementosForm:escolaridade", "Superior");
		dsl.selecionarCombo("elementosForm:esportes", "Natacao");
		
		dsl.clicarBotao("elementosForm:cadastrar");
		
		//Verificações
		Assert.assertTrue(dsl.obterTexto("resultado").startsWith("Cadastrado!"));
		//Assert.assertEquals("Cadastrado!", driver.findElement(By.cssSelector("#resultado span")).getText());
		
		Assert.assertTrue(dsl.obterTexto("descNome").endsWith("Sávio"));
		Assert.assertEquals("Sobrenome: Borges", dsl.obterTexto("descSobrenome"));
		Assert.assertEquals("Sexo: Masculino", dsl.obterTexto("descSexo"));
		Assert.assertEquals("Comida: Pizza", dsl.obterTexto("descComida"));
		Assert.assertEquals("Escolaridade: superior", dsl.obterTexto("descEscolaridade"));
		Assert.assertEquals("Esportes: Natacao", dsl.obterTexto("descEsportes"));	
	}
	
	@Test
	public void ValidarNome() {
		dsl.clicarBotao("elementosForm:cadastrar");
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Nome eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarSobrenome() {
		dsl.escreve("elementosForm:nome", "Sávio");
		dsl.clicarBotao("elementosForm:cadastrar");
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sobrenome eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarSexo() {
		dsl.escreve("elementosForm:nome", "Sávio");
		dsl.escreve("elementosForm:sobrenome", "Borges");
		dsl.clicarBotao("elementosForm:cadastrar");
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sexo eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarComidaVegetariana() {
		dsl.escreve("elementosForm:nome", "Sávio");
		dsl.escreve("elementosForm:sobrenome", "Borges");
		dsl.clicarRadio("elementosForm:sexo:0");
		dsl.clicarRadio("elementosForm:comidaFavorita:0");
		dsl.clicarRadio("elementosForm:comidaFavorita:3");
		dsl.clicarBotao("elementosForm:cadastrar");
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarEsporte() {
		dsl.escreve("elementosForm:nome", "Sávio");
		dsl.escreve("elementosForm:sobrenome", "Borges");
		dsl.clicarRadio("elementosForm:sexo:0");
		dsl.clicarRadio("elementosForm:comidaFavorita:0");
		dsl.selecionarCombo("elementosForm:esportes", "Natacao");
		dsl.selecionarCombo("elementosForm:esportes", "O que eh esporte?");
		dsl.clicarBotao("elementosForm:cadastrar");
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Voce faz esporte ou nao?", alert.getText());
		
		alert.accept();
	}
}
