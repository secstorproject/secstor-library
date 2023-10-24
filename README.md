# Secstor Library 📚

Biblioteca para utilização de algoritmos de compartilhamento de segredos e anonimização de dados para adequação de sistemas à LGPD.

## Requisitos 🛠

- JDK 17 ou superior
- JRE 1.8.0 ou superior
- Lombok Annotations Support for VS Code (extensão para o **Visual Studio Code**)

## Uso da Biblioteca 💡

Clone o repositório

Depois, siga as instruções:

1. Abra o projeto com a IDE ou editor de código de sua preferência;
2. Certifique-se de instalar as dependências presentes no arquivo **pom.xml**;
3. Cheque o exemplo de utilização dentro no pacote **ifsc.secstor.library**;
4. Crie uma classe com o método **main**;
5. Utilize a biblioteca seguindo o exemplo abaixo:

\`\`\`java
import com.ifsc.secstor.library.facade.EngineMaker;

public class Example {
    public static void main(String[] args) throws Exception {
        int N = 10;
        int K = 5;

        EngineMaker engineMaker = new EngineMaker(N, K);

        //Informação a ser dividida e reconstruída
        String data = "teste";

        //Divisão
        engineMaker.split(data, "shamir");

        
        //Printando as chaves geradas, lembre-se de utilizar 
        //o get na engine que você realizou split
        engineMaker.getShamir().getPieces().forEach(System.out::println);
        
        //Reconstrução 
        //N chaves
        String reconstructed = engineMaker.reconstruct("shamir");
        System.out.println("\n" + reconstructed);

        //K chaves ou um número de chaves, onde K <= número de chaves <= N
        reconstructed = engineMaker.reconstruct("shamir", K);
        System.out.println("\n" + reconstructed);
    }
}
\`\`\`

Obs.: os algoritmos disponíveis são **shamir**, **pss**, **css**, **krawczyk** e **pvss**, as classes dentro do pacote **ifsc.secstor.library.facade** estão documentadas seguindo os padrões do **javadoc**.

### Testes de Performance ⏱

Instruções detalhadas para executar os testes de performance estão na classe **OneByOneTest.java** e no arquivo **timing-test-runner.sh**

## Referências 📖

T. Loruenser, A. Happe, D. Slamanig: "ARCHISTAR: Towards Secure and Robust Cloud Based Data Sharing"; Vortrag: Cloud Computing Technology and Science (CloudCom), 2015, Vancouver, Canada; 30.11.2015 - 03.12.2015; in: "CloudCom 2015", IEEE, (2016), S. 371 - 378.

Disponível em: <https://github.com/Archistar/archistar-smc>

---

🌟 Contribuidor Principal: [Acacio-coding](https://github.com/Acacio-coding/Secstor-library)