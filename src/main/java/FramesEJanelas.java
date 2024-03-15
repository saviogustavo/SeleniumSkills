import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FramesEJanelas {
	
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
	public void InteragindoComIframe() {
		driver.switchTo().frame("frame1");
		dsl.clicarBotao("frameButton");
		Alert alert = driver.switchTo().alert();
		String msg = alert.getText();
		alert.accept();
		
		driver.switchTo().defaultContent();
		dsl.escreve("elementosForm:nome", msg);
	}
	
	@Test
	public void InteragindoComJanelas() {
		driver.findElement(By.id("buttonPopUpEasy")).click();
		driver.switchTo().window("Popup");
		
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
		driver.close();
		
		driver.switchTo().window((String)driver.getWindowHandles().toArray()[0]);
		
		//Set<String> handles = driver.getWindowHandles();
		//for(String windowHandle : handles) {        
        //        driver.switchTo().window(windowHandle);
        //}
		
		//driver.switchTo().window("");
		driver.findElement(By.tagName("textarea")).sendKeys("Sávio Gustavo!");
	}
	
	@Test
	public void InteragindoComJanelasSemTitulo() {
		driver.findElement(By.id("buttonPopUpHard")).click();
		driver.switchTo().window((String)driver.getWindowHandles().toArray()[1]);
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
		driver.close();
		
		driver.switchTo().window((String)driver.getWindowHandles().toArray()[0]);
		driver.findElement(By.tagName("textarea")).sendKeys("Confia");
	}
}
