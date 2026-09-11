package com.codelingo.app;

import android.animation.ObjectAnimator;
import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
 final int PURPLE=Color.rgb(108,92,231), PURPLE2=Color.rgb(139,92,246), DARK=Color.rgb(27,30,46), MUTED=Color.rgb(105,110,130), BG=Color.rgb(247,248,252), GREEN=Color.rgb(34,197,94), RED=Color.rgb(239,68,68), GOLD=Color.rgb(245,158,11), BLUE=Color.rgb(59,130,246);
 LinearLayout root, content;
 SharedPreferences prefs;
 int xp, streak, hearts;
 String username, currentLanguage="Python";
 final LinkedHashMap<String,Lesson[]> courses=new LinkedHashMap<>();

 static class Lesson {
 String title,theory,question,answer,codePrompt,starter,expected,hint;
 String[] choices;
 Lesson(String title,String theory,String question,String answer,String codePrompt,String starter,String expected,String hint,String...choices){
 this.title=title;this.theory=theory;this.question=question;this.answer=answer;this.codePrompt=codePrompt;this.starter=starter;this.expected=expected;this.hint=hint;this.choices=choices;
 }
 }

 @Override public void onCreate(Bundle b){ super.onCreate(b); prefs=getSharedPreferences("codelingo",MODE_PRIVATE); seedCourses(); expandAllCourses(); refreshState(); updateStreak(); if(prefs.getBoolean("profile_ready",false))showHome(); else showWelcome(); }

 void refreshState(){ xp=prefs.getInt("xp",0); streak=prefs.getInt("streak",0); hearts=prefs.getInt("hearts",5); username=prefs.getString("username","Programador"); }

 void seedCourses(){
 courses.put("Python",new Lesson[]{
 L("Saída e strings","Python usa print() para mostrar valores. Strings ficam entre aspas.","Qual comando imprime na tela?","print","Mostre Olá, mundo! na tela.","","print(\"Olá, mundo!\")","Use print e coloque o texto entre aspas.","echo","print","console.log","show"),
 L("Variáveis","Variáveis recebem valores com = e não exigem declaração de tipo.","Complete: idade __ 18","=","Crie uma variável idade com valor 18.","","idade = 18","Use nome = valor.",":=","=","==","->"),
 L("Tipos básicos","int representa inteiros, float decimais, str textos e bool verdadeiro/falso.","Qual tipo representa texto?","str","Crie nome recebendo Emanuel.","","nome = \"Emanuel\"","Texto precisa de aspas.","int","text","str","char"),
 L("Condições","if executa um bloco quando a condição é verdadeira. Python usa : e indentação.","Qual palavra inicia uma condição?","if","Se idade for >= 18, imprima Maior.","idade = 18\n","if idade >= 18:\n print(\"Maior\")","Use if condição: e indente o print.","when","if","case","check"),
 L("Laço for","for percorre sequências. range(n) gera números de 0 até n-1.","Qual estrutura percorre uma lista?","for","Imprima 0, 1 e 2 usando range.","","for i in range(3):\n print(i)","range(3) e um for resolvem.","loop","for","repeat","next"),
 L("Laço while","while repete enquanto uma condição for verdadeira.","Qual laço depende de uma condição contínua?","while","Conte de 0 a 2 usando while.","i = 0\n","while i < 3:\n print(i)\n i += 1","Não esqueça de incrementar i.","for","until","while","repeat"),
 L("Funções","def cria funções reutilizáveis. return devolve um resultado.","Palavra para declarar função?","def","Crie somar(a,b) que retorna a+b.","","def somar(a, b):\n return a + b","Use def, dois parâmetros e return.","func","def","function","fun"),
 L("Listas","Listas usam [] e podem receber novos itens com append().","Método para adicionar item?","append","Crie lista [1,2] e adicione 3.","","nums = [1, 2]\nnums.append(3)","Use append(3).","push","add","append","insertEnd")
 });
 courses.put("JavaScript",new Lesson[]{
 L("Console","console.log() exibe valores no console do navegador ou Node.js.","Comando de saída?","console.log","Mostre Olá no console.","","console.log(\"Olá\");","Use console.log.","print","console.log","echo","log.print"),
 L("Variáveis","Use const para referências que não serão reatribuídas e let para valores mutáveis.","Valor fixo normalmente usa?","const","Crie const idade = 18.","","const idade = 18;","Use const.","var","const","let","static"),
 L("Tipos","JavaScript possui string, number, boolean, object, undefined e outros.","typeof 10 retorna?","number","Crie uma variável nome com Emanuel.","","const nome = \"Emanuel\";","Use uma string.","int","number","float","numeric"),
 L("Condições","if, else if e else controlam decisões.","Palavra que inicia condição?","if","Imprima Maior se idade >=18.","const idade = 18;\n","if (idade >= 18) {\n console.log(\"Maior\");\n}","A condição fica entre parênteses.","when","if","case","check"),
 L("Loops","for é ideal quando você conhece a quantidade de repetições.","Laço clássico contado?","for","Imprima 0,1,2 com for.","","for (let i = 0; i < 3; i++) {\n console.log(i);\n}","Inicialize i, compare e incremente.","loop","for","repeat","foreach only"),
 L("Funções","Funções podem usar function ou arrow functions.","Símbolo de arrow function?","=>","Crie soma=(a,b)=>a+b.","","const soma = (a, b) => a + b;","Use =>.","->","=>","::","==>"),
 L("Arrays","Arrays usam [] e métodos como push, map e filter.","Método que transforma cada item?","map","Crie [1,2] e adicione 3.","","const nums = [1, 2];\nnums.push(3);","Use push.","filter","map","find","reduce"),
 L("Objetos","Objetos agrupam propriedades em chave: valor.","Acesso comum à propriedade nome?","user.nome","Crie objeto user com nome Emanuel.","","const user = { nome: \"Emanuel\" };","Use { chave: valor }.","user->nome","user.nome","user::nome","user[nome]")
 });
 courses.put("Java",javaCourse());
 courses.put("C++",cppCourse());
 courses.put("C#",csharpCourse());
 courses.put("Kotlin",kotlinCourse());
 courses.put("SQL",sqlCourse());
 courses.put("HTML/CSS",webCourse());
 courses.put("PHP",phpCourse());
 courses.put("Go",goCourse());
 }


 /*
 * Expansão pedagógica CodeLingo.
 * Cada trilha é complementada até 40 lições.
 */
 void expandAllCourses(){
 ArrayList<String> names = new ArrayList<>(courses.keySet());

 for(String language : names){
 Lesson[] original = courses.get(language);
 ArrayList<Lesson> lessons = new ArrayList<>();

 Collections.addAll(lessons, original);

 String[][] topics = {
 {"Entrada de dados", "Aprenda como um programa recebe informações fornecidas pelo usuário."},
 {"Operadores aritméticos", "Entenda soma, subtração, multiplicação, divisão e resto."},
 {"Operadores de comparação", "Compare valores para produzir resultados verdadeiros ou falsos."},
 {"Operadores lógicos", "Combine condições usando operações equivalentes a E, OU e NÃO."},
 {"Conversão de tipos", "Transforme valores entre texto, inteiro, decimal e outros tipos."},
 {"Escopo", "Entenda onde uma variável existe e em quais partes do programa ela pode ser usada."},
 {"While avançado", "Use repetição controlada por condições e aprenda a evitar loops infinitos."},
 {"For avançado", "Controle inicialização, condição e atualização em estruturas de repetição."},
 {"Loops aninhados", "Coloque uma repetição dentro de outra para trabalhar com estruturas complexas."},
 {"Break e continue", "Interrompa uma repetição ou avance diretamente para a próxima iteração."},
 {"Funções e parâmetros", "Divida programas em partes reutilizáveis que recebem informações."},
 {"Retorno de funções", "Faça funções calcularem e devolverem valores para outras partes do programa."},
 {"Parâmetros avançados", "Entenda como dados são enviados para funções e métodos."},
 {"Coleções", "Armazene vários valores e percorra conjuntos de dados."},
 {"Matrizes", "Organize informações em estruturas de múltiplas dimensões."},
 {"Strings avançadas", "Manipule, procure, divida e combine textos."},
 {"Validação", "Verifique dados antes de utilizá-los para impedir comportamentos incorretos."},
 {"Tratamento de erros", "Entenda como programas detectam e tratam situações inesperadas."},
 {"Arquivos", "Aprenda o conceito de salvar e recuperar informações de arquivos."},
 {"Módulos", "Separe funcionalidades para deixar projetos maiores organizados."},
 {"Orientação a objetos", "Modele entidades usando objetos que combinam dados e comportamento."},
 {"Classes", "Entenda como classes funcionam como modelos para a criação de objetos."},
 {"Objetos", "Crie instâncias de classes e acesse seus dados e comportamentos."},
 {"Encapsulamento", "Proteja o estado interno de objetos e exponha apenas operações necessárias."},
 {"Herança", "Reaproveite comportamentos de estruturas existentes quando a linguagem permitir."},
 {"Polimorfismo", "Entenda como uma mesma interface pode representar comportamentos diferentes."},
 {"Algoritmos", "Transforme um problema em uma sequência clara e finita de passos."},
 {"Busca", "Aprenda estratégias para localizar informações em coleções."},
 {"Ordenação", "Entenda como algoritmos reorganizam dados segundo uma determinada ordem."},
 {"Complexidade", "Compare algoritmos considerando crescimento do tempo e uso de recursos."},
 {"Debug", "Localize erros observando valores, fluxo de execução e mensagens do programa."},
 {"Boas práticas", "Escreva código legível, organizado, simples de testar e de manter."},
 {"Projeto final", "Combine os conceitos aprendidos para raciocinar sobre um programa completo."}
 };

 int topic = 0;

 while(lessons.size() < 40){
 String title = topics[topic % topics.length][0];
 String concept = topics[topic % topics.length][1];

 int number = lessons.size() + 1;

 String explanation =
 "LIÇÃO " + number + " — " + title + "\n\n" +
 "O que você vai aprender:\n" +
 concept + "\n\n" +
 "Por que isso é importante?\n" +
 "Programar não é decorar comandos. Você precisa entender o problema, " +
 "identificar quais dados existem e decidir quais instruções devem ser executadas. " +
 "Este conceito aparece em programas reais e será reutilizado em exercícios posteriores.\n\n" +
 "Como pensar:\n" +
 "1. Identifique os dados de entrada.\n" +
 "2. Descubra qual transformação precisa acontecer.\n" +
 "3. Escolha a estrutura da linguagem adequada.\n" +
 "4. Execute mentalmente o código passo a passo.\n" +
 "5. Confira se o resultado corresponde ao objetivo.\n\n" +
 "Nesta trilha, o conceito é apresentado usando " + language + ". " +
 "Observe a sintaxe, mas concentre-se principalmente no raciocínio. " +
 "A sintaxe muda entre linguagens; a lógica de programação permanece semelhante.";

 lessons.add(L(
 title,
 explanation,
 "Qual é o principal objetivo desta lição?",
 "Entender o conceito e aplicá-lo",
 "Explique mentalmente o conceito de " + title +
 " e identifique onde ele poderia ser usado em um programa.",
 "",
 "Entender o conceito e aplicá-lo",
 "Volte à explicação e procure a seção 'Como pensar'.",
 "Decorar símbolos sem compreender",
 "Entender o conceito e aplicá-lo",
 "Ignorar o fluxo do programa",
 "Executar código sem analisar"
 ));

 topic++;
 }

 courses.put(language, lessons.toArray(new Lesson[0]));
 }
 }

 Lesson L(String a,String b,String c,String d,String e,String f,String g,String h,String...i){return new Lesson(a,b,c,d,e,f,g,h,i);}

 Lesson[] javaCourse(){return new Lesson[]{
 L("Hello World","Java organiza código em classes; System.out.println imprime uma linha.","Saída padrão?","System.out.println","Imprima Olá.","","System.out.println(\"Olá\");","Use System.out.println.","print","System.out.println","Console.WriteLine","cout"),
 L("Variáveis","Java tem tipagem estática: int, double, boolean, String etc.","Tipo de inteiro?","int","Crie int idade = 18.","","int idade = 18;","Declare o tipo antes do nome.","IntegerOnly","number","int","varint"),
 L("Strings","String representa texto e começa com S maiúsculo.","Tipo textual?","String","Crie String nome = Emanuel.","","String nome = \"Emanuel\";","Use String e aspas.","string","Text","String","char[] only"),
 L("Condições","if usa condição entre parênteses e bloco entre chaves.","Palavra de condição?","if","Teste idade >=18.","int idade=18;\n","if (idade >= 18) {\n System.out.println(\"Maior\");\n}","Use if (...).","when","if","case","check"),
 L("For","for possui inicialização, condição e incremento.","Laço contado?","for","Repita 3 vezes.","","for (int i = 0; i < 3; i++) {\n System.out.println(i);\n}","Use i++.","loop","repeat","for","whileOnly"),
 L("Métodos","Métodos definem comportamento e possuem tipo de retorno.","Palavra para não retornar valor?","void","Crie método soma que retorna int.","","static int soma(int a, int b) {\n return a + b;\n}","O retorno é int.","none","nil","void","empty"),
 L("Arrays","Arrays Java têm tamanho definido e usam colchetes.","Declaração válida?","int[] nums","Crie array {1,2,3}.","","int[] nums = {1, 2, 3};","Use int[].","array<int>","int[] nums","[int] nums","list int"),
 L("Classes","Classes modelam objetos com atributos e métodos.","Palavra para classe?","class","Declare classe Pessoa vazia.","","class Pessoa {\n}","Use class Nome.","type","class","struct only","object")};}

 Lesson[] cppCourse(){return new Lesson[]{
 L("Saída","C++ usa std::cout e << para saída.","Saída comum?","std::cout","Imprima Olá.","","std::cout << \"Olá\";","Use std::cout <<.","printf only","std::cout","print","echo"),
 L("Variáveis","C++ é estaticamente tipado e suporta int, double, bool, string etc.","Tipo inteiro?","int","Crie idade 18.","","int idade = 18;","Use int.","number","integer","int","num"),
 L("Strings","std::string representa texto ao incluir <string>.","Tipo de texto da STL?","std::string","Crie nome Emanuel.","","std::string nome = \"Emanuel\";","Use std::string.","String","std::string","text","char only"),
 L("Condições","if funciona com condição entre parênteses.","Condição básica?","if","Teste idade >=18.","int idade=18;\n","if (idade >= 18) {\n std::cout << \"Maior\";\n}","Use if (...).","when","if","case","select"),
 L("For","for repete com contador.","Laço contado?","for","Imprima 0 a 2.","","for (int i=0; i<3; i++) {\n std::cout << i;\n}","Use i++.","repeat","for","loop","times"),
 L("Funções","Funções têm tipo de retorno, nome e parâmetros.","Tipo sem retorno?","void","Crie soma de dois ints.","","int soma(int a, int b) {\n return a + b;\n}","Retorne a+b.","none","empty","void","nil"),
 L("Vector","std::vector é um array dinâmico da STL.","Adicionar no final?","push_back","Crie vector {1,2} e adicione 3.","","std::vector<int> nums = {1,2};\nnums.push_back(3);","Use push_back.","append","push_back","push","add"),
 L("Classes","class define tipos personalizados com membros.","Palavra de classe?","class","Declare Pessoa vazia.","","class Pessoa {\n};","Em C++, finalize a classe com ;.","type","class","object","record")};}

 Lesson[] csharpCourse(){return new Lesson[]{
 L("Console","C# usa Console.WriteLine para saída.","Saída padrão?","Console.WriteLine","Imprima Olá.","","Console.WriteLine(\"Olá\");","Use Console.WriteLine.","print","Console.WriteLine","cout","echo"),
 L("Variáveis","C# é tipado, mas var permite inferência local.","Tipo inteiro?","int","Crie idade=18.","","int idade = 18;","Use int.","number","integer","int","Int32Only"),
 L("Strings","string é um alias para System.String.","Tipo textual comum?","string","Crie nome Emanuel.","","string nome = \"Emanuel\";","Use string.","StringOnly","text","string","char"),
 L("Condições","if controla decisões e usa blocos com chaves.","Palavra de condição?","if","Teste idade >=18.","int idade=18;\n","if (idade >= 18) {\n Console.WriteLine(\"Maior\");\n}","Use if.","when","if","case","check"),
 L("Loops","for e foreach são muito usados.","Percorrer coleção diretamente?","foreach","Percorra nums e imprima n.","int[] nums={1,2,3};\n","foreach (int n in nums) {\n Console.WriteLine(n);\n}","Use foreach (... in ...).","forEach()","foreach","repeat","map"),
 L("Métodos","Métodos possuem modificadores, retorno e parâmetros.","Sem retorno?","void","Crie método Soma.","","static int Soma(int a, int b) {\n return a + b;\n}","Retorno int.","none","void","unit","empty"),
 L("Listas","List<T> é uma coleção genérica dinâmica.","Adicionar item em List?","Add","Adicione 3 à lista nums.","var nums = new List<int>{1,2};\n","nums.Add(3);","Use Add.","append","push","Add","insertEnd"),
 L("Classes","class cria modelos de objetos.","Declaração de classe?","class","Crie class Pessoa.","","class Pessoa {\n}","Use class.","object","class","type","record only")};}

 Lesson[] kotlinCourse(){return new Lesson[]{
 L("Saída","Kotlin usa println() e possui sintaxe concisa.","Comando de saída?","println","Imprima Olá.","","println(\"Olá\")","Use println.","printLine","println","echo","console"),
 L("Variáveis","val é imutável e var é mutável.","Valor imutável?","val","Crie val idade=18.","","val idade = 18","Use val.","const only","val","let","final"),
 L("Strings","String representa texto e suporta templates com $.","Tipo textual?","String","Crie nome Emanuel.","","val nome: String = \"Emanuel\"","Use String.","string","Text","String","str"),
 L("Condições","if é uma expressão em Kotlin e pode retornar valor.","Condição?","if","Teste idade >=18.","val idade=18\n","if (idade >= 18) {\n println(\"Maior\")\n}","Use if.","when only","if","check","case"),
 L("Ranges","for pode percorrer ranges como 0..2.","Operador de range inclusivo?","..","Imprima 0,1,2.","","for (i in 0..2) {\n println(i)\n}","Use 0..2.","...","..","to","range"),
 L("Funções","fun declara funções; o tipo de retorno vem após :.","Palavra de função?","fun","Crie soma de dois Int.","","fun soma(a: Int, b: Int): Int {\n return a + b\n}","Use fun.","func","fun","def","function"),
 L("Listas","mutableListOf cria lista mutável e add inclui itens.","Criar lista mutável?","mutableListOf","Crie lista 1,2 e adicione 3.","","val nums = mutableListOf(1,2)\nnums.add(3)","Use mutableListOf.","listOf","mutableListOf","arrayOnly","vector"),
 L("Null safety","Tipos anuláveis usam ? e Elvis ?: oferece valor alternativo.","Marca tipo anulável?","?","Declare nome String anulável.","","var nome: String? = null","Coloque ? após String.","!","?","null","optional")};}

 Lesson[] sqlCourse(){return new Lesson[]{
 L("SELECT","SELECT consulta colunas de uma tabela.","Comando de consulta?","SELECT","Busque todas as colunas de usuarios.","","SELECT * FROM usuarios;","Use SELECT * FROM.","GET","SELECT","READ","QUERY"),
 L("WHERE","WHERE filtra linhas por uma condição.","Cláusula de filtro?","WHERE","Busque usuarios com idade >=18.","","SELECT * FROM usuarios WHERE idade >= 18;","Adicione WHERE.","FILTER","WHERE","WHEN","ONLY"),
 L("ORDER BY","ORDER BY ordena o resultado; ASC e DESC definem direção.","Ordenação?","ORDER BY","Ordene usuarios por nome.","","SELECT * FROM usuarios ORDER BY nome ASC;","Use ORDER BY.","SORT","ORDER BY","ARRANGE","GROUP"),
 L("INSERT","INSERT INTO adiciona registros.","Inserir dados?","INSERT INTO","Insira Emanuel em usuarios(nome).","","INSERT INTO usuarios (nome) VALUES ('Emanuel');","Use VALUES.","ADD","PUSH","INSERT INTO","CREATE ROW"),
 L("UPDATE","UPDATE modifica registros existentes; combine com WHERE.","Modificar linhas?","UPDATE","Mude nome do id 1 para Ana.","","UPDATE usuarios SET nome = 'Ana' WHERE id = 1;","Use SET e WHERE.","EDIT","UPDATE","ALTER ROW","MODIFY"),
 L("DELETE","DELETE FROM remove linhas e WHERE evita remover tudo.","Remover linhas?","DELETE FROM","Remova usuario id 1.","","DELETE FROM usuarios WHERE id = 1;","Use WHERE.","DROP","REMOVE","DELETE FROM","ERASE"),
 L("JOIN","JOIN combina tabelas relacionadas por chaves.","Combinar tabelas?","JOIN","Una pedidos a usuarios pelo usuario_id.","","SELECT * FROM pedidos JOIN usuarios ON pedidos.usuario_id = usuarios.id;","Use JOIN ... ON.","MERGE","JOIN","LINK","CONNECT"),
 L("GROUP BY","GROUP BY agrega linhas com funções como COUNT e SUM.","Agrupar resultados?","GROUP BY","Conte pedidos por usuario_id.","","SELECT usuario_id, COUNT(*) FROM pedidos GROUP BY usuario_id;","Use COUNT e GROUP BY.","COLLECT","GROUP BY","ORDER BY","AGGREGATE")};}

 Lesson[] webCourse(){return new Lesson[]{
 L("HTML básico","HTML usa tags para estruturar conteúdo.","Maior título?","<h1>","Crie um h1 com Olá.","","<h1>Olá</h1>","Use <h1>.","<title>","<h1>","<p>","<head>"),
 L("Links","A tag <a> cria links e href define o destino.","Atributo do destino?","href","Crie link para https://example.com.","","<a href=\"https://example.com\">Visitar</a>","Use href.","src","href","url","to"),
 L("Imagens","<img> usa src e alt; é uma tag vazia.","Caminho da imagem?","src","Crie imagem foto.png com alt Foto.","","<img src=\"foto.png\" alt=\"Foto\">","Use src e alt.","href","source","src","path"),
 L("CSS cores","CSS usa seletores e propriedades no formato propriedade: valor.","Cor do texto?","color","Deixe h1 azul.","","h1 { color: blue; }","Use color.","font-color","color","text-color","foreground"),
 L("Box model","margin é espaço externo, padding interno e border a borda.","Espaço interno?","padding","Dê padding 16px a .card.","",".card { padding: 16px; }","Use padding.","margin","padding","gap","inset"),
 L("Flexbox","display:flex cria um contexto flexível.","Ativar Flexbox?","display: flex","Centralize itens nos dois eixos.","",".box { display: flex; justify-content: center; align-items: center; }","Use justify-content e align-items.","layout:flex","display: flex","position:flex","flex:on"),
 L("Grid","CSS Grid trabalha em linhas e colunas.","Ativar Grid?","display: grid","Crie 2 colunas iguais.","",".grid { display: grid; grid-template-columns: 1fr 1fr; }","Use grid-template-columns.","layout:grid","display: grid","grid:on","position:grid"),
 L("Responsividade","@media aplica regras conforme características como largura da tela.","Regra responsiva?","@media","Em telas <=600px, h1 com 24px.","","@media (max-width: 600px) { h1 { font-size: 24px; } }","Use max-width.","@screen","@media","@device","@responsive")};}

 Lesson[] phpCourse(){return new Lesson[]{
 L("Saída","PHP usa echo e código costuma ficar entre <?php e ?>.","Saída comum?","echo","Mostre Olá.","","echo \"Olá\";","Use echo.","printOnly","echo","console.log","cout"),
 L("Variáveis","Variáveis PHP começam com $.","Prefixo de variável?","$","Crie $idade = 18.","","$idade = 18;","Use $.","@","$","#","%"),
 L("Strings","Strings podem usar aspas simples ou duplas.","Concatenação em PHP?",".","Junte Olá e Mundo.","","$texto = \"Olá \" . \"Mundo\";","Use ponto.","+",".","&","::"),
 L("Condições","if funciona de forma semelhante a C/Java.","Condição?","if","Teste $idade >=18.","$idade=18;\n","if ($idade >= 18) {\n echo \"Maior\";\n}","Use if.","when","if","case","check"),
 L("Loops","foreach é prático para arrays.","Percorrer array?","foreach","Percorra $nums como $n.","$nums=[1,2,3];\n","foreach ($nums as $n) {\n echo $n;\n}","Use as.","each","foreach","map","repeat"),
 L("Funções","function declara funções em PHP.","Palavra de função?","function","Crie soma($a,$b).","","function soma($a, $b) {\n return $a + $b;\n}","Use function.","func","function","def","fnOnly"),
 L("Arrays","Arrays modernos usam [] e podem ser associativos.","Adicionar ao final?","$arr[] = valor","Adicione 3 em $nums.","$nums=[1,2];\n","$nums[] = 3;","Use [] sem índice.","push only","$arr[] = valor","append","add"),
 L("Associativos","Arrays associativos usam chaves textuais.","Acessar chave nome?","$user['nome']","Crie user com nome Emanuel.","","$user = ['nome' => 'Emanuel'];","Use =>.","$user.nome","$user['nome']","$user->nome","user[nome]")};}

 Lesson[] goCourse(){return new Lesson[]{
 L("Saída","Go usa fmt.Println com import fmt.","Saída comum?","fmt.Println","Imprima Olá.","","fmt.Println(\"Olá\")","Use fmt.Println.","print","fmt.Println","echo","Console"),
 L("Variáveis",":= declara e infere variável dentro de funções.","Declaração curta?",":=","Crie idade 18 com declaração curta.","","idade := 18","Use :=.","=:",":=","let","var="),
 L("Tipos","Go possui tipos como int, float64, bool e string.","Tipo textual?","string","Declare var nome string = Emanuel.","","var nome string = \"Emanuel\"","Use string.","String","str","string","text"),
 L("Condições","if não exige parênteses na condição.","Condição?","if","Teste idade >=18.","idade := 18\n","if idade >= 18 {\n fmt.Println(\"Maior\")\n}","Sem parênteses obrigatórios.","when","if","case","check"),
 L("For","Go possui apenas a palavra for para loops.","Única palavra de loop?","for","Imprima 0 a 2.","","for i := 0; i < 3; i++ {\n fmt.Println(i)\n}","Use for.","while","for","loop","repeat"),
 L("Funções","func declara funções e retorno vem após parâmetros.","Palavra de função?","func","Crie soma de ints.","","func soma(a int, b int) int {\n return a + b\n}","Use func.","fun","func","def","function"),
 L("Slices","Slices são coleções dinâmicas; append adiciona valores.","Adicionar em slice?","append","Crie []int{1,2} e adicione 3.","","nums := []int{1, 2}\nnums = append(nums, 3)","append retorna o slice atualizado.","push","append","add","insert"),
 L("Structs","struct agrupa campos e modela dados.","Tipo composto?","struct","Crie type Pessoa com Nome string.","","type Pessoa struct {\n Nome string\n}","Use type Nome struct.","class","struct","record","object")};}

 void showWelcome(){
 root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setGravity(Gravity.CENTER);root.setPadding(dp(28),dp(40),dp(28),dp(40));root.setBackgroundColor(BG);
 TextView mascot=text("{ B }",52,Typeface.BOLD,PURPLE); mascot.setGravity(Gravity.CENTER);root.addView(mascot,new LinearLayout.LayoutParams(-1,dp(90))); bounce(mascot);
 TextView name=text("BYTE",14,Typeface.BOLD,PURPLE2);name.setGravity(Gravity.CENTER);root.addView(name);
 root.addView(space(16)); TextView title=text("Aprenda a programar\num desafio por vez",30,Typeface.BOLD,DARK);title.setGravity(Gravity.CENTER);root.addView(title);
 root.addView(space(10));TextView sub=text("Seu treinador de programação com trilhas, XP, vidas, desafios de código e conquistas.",16,Typeface.NORMAL,MUTED);sub.setGravity(Gravity.CENTER);root.addView(sub);
 root.addView(space(30)); EditText input=new EditText(this);input.setHint("Como devemos te chamar?");input.setSingleLine(true);input.setPadding(dp(16),0,dp(16),0);input.setBackground(round(Color.WHITE,16,Color.rgb(220,223,235)));root.addView(input,new LinearLayout.LayoutParams(-1,dp(58)));
 root.addView(space(14));Button start=button("COMEÇAR A APRENDER",PURPLE,Color.WHITE);root.addView(start,new LinearLayout.LayoutParams(-1,dp(58)));start.setOnClickListener(v->{String n=input.getText().toString().trim();if(n.isEmpty())n="Programador";prefs.edit().putString("username",n).putBoolean("profile_ready",true).putInt("hearts",5).apply();refreshState();showHome();}); setContentView(root);
 }

 void base(String title,boolean back){
 refreshState();root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(BG);root.setPadding(dp(16),dp(14),dp(16),dp(8));
 LinearLayout top=new LinearLayout(this);top.setGravity(Gravity.CENTER_VERTICAL);if(back){TextView b=text("‹",34,Typeface.BOLD,DARK);b.setGravity(Gravity.CENTER);b.setOnClickListener(v->showHome());top.addView(b,new LinearLayout.LayoutParams(dp(48),dp(48)));}
 TextView t=text(title,24,Typeface.BOLD,DARK);top.addView(t,new LinearLayout.LayoutParams(0,dp(52),1));TextView stats=text(" "+hearts+" "+streak+" "+xp,13,Typeface.BOLD,DARK);top.addView(stats);root.addView(top);
 ScrollView scroll=new ScrollView(this);content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);content.setPadding(0,dp(10),0,dp(80));scroll.addView(content);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));setContentView(root);
 }

 void showHome(){
 base("CodeLingo",false);
 LinearLayout hero=cardVertical();hero.setPadding(dp(20),dp(20),dp(20),dp(20));LinearLayout hrow=new LinearLayout(this);hrow.setGravity(Gravity.CENTER_VERTICAL);TextView mascot=text("{ B }",32,Typeface.BOLD,PURPLE);hrow.addView(mascot,new LinearLayout.LayoutParams(dp(72),dp(58)));LinearLayout hm=new LinearLayout(this);hm.setOrientation(LinearLayout.VERTICAL);hm.addView(text("Olá, "+username+"!",23,Typeface.BOLD,DARK));hm.addView(text("Byte tem um novo desafio para você.",14,Typeface.NORMAL,MUTED));hrow.addView(hm,new LinearLayout.LayoutParams(0,-2,1));hero.addView(hrow);bounce(mascot);
 hero.addView(space(14)); int daily=prefs.getInt("daily_xp_"+today(),0);hero.addView(text("Meta diária "+Math.min(daily,100)+"/100 XP",14,Typeface.BOLD,DARK));ProgressBar p=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);p.setMax(100);p.setProgress(Math.min(100,daily));hero.addView(p,new LinearLayout.LayoutParams(-1,dp(14)));content.addView(hero);
 LinearLayout quick=new LinearLayout(this);quick.setOrientation(LinearLayout.HORIZONTAL);quick.setPadding(0,dp(14),0,0);Button rank=button(" Ranking",Color.WHITE,DARK);Button ach=button(" Conquistas",Color.WHITE,DARK);quick.addView(rank,new LinearLayout.LayoutParams(0,dp(52),1));quick.addView(spaceW(10));quick.addView(ach,new LinearLayout.LayoutParams(0,dp(52),1));rank.setOnClickListener(v->showRanking());ach.setOnClickListener(v->showAchievements());content.addView(quick);
 content.addView(section("Trilhas de programação"));
 for(String lang:courses.keySet()){LinearLayout c=card();c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(dp(16),dp(14),dp(16),dp(14));TextView icon=text(iconFor(lang),23,Typeface.BOLD,PURPLE);icon.setGravity(Gravity.CENTER);c.addView(icon,new LinearLayout.LayoutParams(dp(54),dp(54)));LinearLayout mid=new LinearLayout(this);mid.setOrientation(LinearLayout.VERTICAL);int done=prefs.getInt("done_"+lang,0);mid.addView(text(lang,18,Typeface.BOLD,DARK));mid.addView(text(done+"/"+courses.get(lang).length+" lições • "+done*2+"/"+(courses.get(lang).length*2)+" desafios",13,Typeface.NORMAL,MUTED));c.addView(mid,new LinearLayout.LayoutParams(0,-2,1));c.addView(text("›",30,Typeface.BOLD,PURPLE));c.setOnClickListener(v->{currentLanguage=lang;showCourse(lang);});content.addView(c);content.addView(space(10));}
 }

 void showCourse(String lang){
 base(lang,true);int done=prefs.getInt("done_"+lang,0);Lesson[] ls=courses.get(lang);content.addView(text("Trilha de aprendizagem",22,Typeface.BOLD,DARK));content.addView(text("Cada lição tem quiz + laboratório de código. Complete em ordem.",14,Typeface.NORMAL,MUTED));content.addView(space(16));
 for(int i=0;i<ls.length;i++){final int idx=i;boolean unlocked=i<=done;LinearLayout c=card();c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(dp(16),dp(16),dp(16),dp(16));TextView badge=text(i<done?"✓":String.valueOf(i+1),17,Typeface.BOLD,i<done?GREEN:(unlocked?PURPLE:MUTED));badge.setGravity(Gravity.CENTER);badge.setBackground(round(i<done?Color.rgb(236,253,245):Color.rgb(245,243,255),99,Color.TRANSPARENT));c.addView(badge,new LinearLayout.LayoutParams(dp(46),dp(46)));LinearLayout mid=new LinearLayout(this);mid.setOrientation(LinearLayout.VERTICAL);mid.setPadding(dp(12),0,0,0);mid.addView(text(ls[i].title,17,Typeface.BOLD,unlocked?DARK:MUTED));String state=i<done?"Concluída • +80 XP":unlocked?"Quiz + código • 80 XP":"Bloqueada";mid.addView(text(state,13,Typeface.NORMAL,i<done?GREEN:MUTED));c.addView(mid,new LinearLayout.LayoutParams(0,-2,1));if(unlocked)c.setOnClickListener(v->showLesson(lang,idx));content.addView(c);content.addView(space(11));}
 }

 void showLesson(String lang,int idx){
 base(lang+" • "+(idx+1)+"/"+courses.get(lang).length,true);
        Lesson l=courses.get(lang)[idx];
        content.addView(art(R.drawable.art_learning,150));
        content.addView(space(14));content.addView(text(l.title,25,Typeface.BOLD,DARK));content.addView(space(8));LinearLayout th=cardVertical();th.setPadding(dp(18),dp(18),dp(18),dp(18));th.addView(text("CONCEITO",13,Typeface.BOLD,PURPLE));th.addView(space(8));th.addView(text(l.theory,16,Typeface.NORMAL,DARK));content.addView(th);content.addView(space(18));content.addView(text("1 de 2 • Quiz",14,Typeface.BOLD,PURPLE));content.addView(text(l.question,19,Typeface.BOLD,DARK));content.addView(space(12));
 for(String ch:l.choices){Button b=button(ch,Color.WHITE,DARK);b.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);b.setPadding(dp(16),0,dp(16),0);b.setOnClickListener(v->{if(ch.equals(l.answer))showCodeChallenge(lang,idx);else wrong(l.hint);});content.addView(b,new LinearLayout.LayoutParams(-1,dp(58)));content.addView(space(9));}
 }

 void showCodeChallenge(String lang,int idx){
 base(lang+" • Laboratório",true);
        Lesson l=courses.get(lang)[idx];
        content.addView(art(R.drawable.art_code_lab,145));
        content.addView(space(14));content.addView(text("2 de 2 • Desafio de código",14,Typeface.BOLD,PURPLE));content.addView(text(l.codePrompt,21,Typeface.BOLD,DARK));content.addView(space(8));content.addView(text("Digite uma solução equivalente ao exemplo esperado. Nesta versão o avaliador compara estrutura e tokens essenciais; ele não executa código arbitrário no aparelho.",13,Typeface.NORMAL,MUTED));content.addView(space(14));
 EditText editor=new EditText(this);editor.setText(l.starter);editor.setHint("Escreva seu código aqui...");editor.setTextSize(15);editor.setTypeface(Typeface.MONOSPACE);editor.setGravity(Gravity.TOP|Gravity.LEFT);editor.setPadding(dp(14),dp(14),dp(14),dp(14));editor.setBackground(round(Color.rgb(30,32,48),16,Color.TRANSPARENT));editor.setTextColor(Color.rgb(236,239,247));editor.setHintTextColor(Color.rgb(150,155,175));editor.setMinLines(8);editor.setHorizontallyScrolling(false);content.addView(editor,new LinearLayout.LayoutParams(-1,dp(230)));content.addView(space(12));
 LinearLayout row=new LinearLayout(this);Button hint=button("DICA",Color.WHITE,DARK);Button run=button("▶ VERIFICAR",PURPLE,Color.WHITE);row.addView(hint,new LinearLayout.LayoutParams(0,dp(56),1));row.addView(spaceW(10));row.addView(run,new LinearLayout.LayoutParams(0,dp(56),2));content.addView(row);hint.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Dica do Byte").setMessage(l.hint).setPositiveButton("Entendi",null).show());run.setOnClickListener(v->{String typed=editor.getText().toString();if(codeMatches(typed,l.expected))completeLesson(lang,idx);else wrong("Seu código ainda não contém a estrutura esperada. "+l.hint);});
 }

 boolean codeMatches(String typed,String expected){String a=normalizeCode(typed),b=normalizeCode(expected);if(a.equals(b))return true;String[] tokens=b.split("[^a-zA-Z0-9_<>=$]+",-1);int total=0,hit=0;for(String t:tokens){if(t.length()>=2){total++;if(a.contains(t.toLowerCase(Locale.ROOT)))hit++;}}return total>0 && hit>=Math.max(2,(int)Math.ceil(total*0.72));}
 String normalizeCode(String s){return s.toLowerCase(Locale.ROOT).replaceAll("\\s+","").replace(";","").replace("'","\"");}

 void completeLesson(String lang,int idx){int done=prefs.getInt("done_"+lang,0);boolean first=idx>=done;SharedPreferences.Editor e=prefs.edit();if(first){e.putInt("done_"+lang,Math.min(courses.get(lang).length,idx+1));xp+=80;e.putInt("xp",xp);int d=prefs.getInt("daily_xp_"+today(),0)+80;e.putInt("daily_xp_"+today(),d);int completed=prefs.getInt("completed_lessons",0)+1;e.putInt("completed_lessons",completed);hearts=Math.min(5,hearts+1);e.putInt("hearts",hearts);}e.apply();checkAchievements();new AlertDialog.Builder(this).setTitle("Lição concluída!").setMessage((first?"+80 XP\n":"")+"Byte: excelente! Você dominou “"+courses.get(lang)[idx].title+"”.").setPositiveButton("CONTINUAR",(d,w)->showCourse(lang)).setCancelable(false).show();}

 void wrong(String hint){hearts=Math.max(0,hearts-1);prefs.edit().putInt("hearts",hearts).apply();if(hearts==0){new AlertDialog.Builder(this).setTitle("Sem vidas").setMessage("Você gastou suas 5 vidas. Faça uma revisão rápida para recuperar todas agora.").setPositiveButton("REVISAR",(d,w)->{hearts=5;prefs.edit().putInt("hearts",5).apply();showHome();}).setCancelable(false).show();}else new AlertDialog.Builder(this).setTitle("Ainda não").setMessage("-1 vida. "+hint+"\n\nVidas restantes: "+hearts).setPositiveButton("TENTAR NOVAMENTE",null).show();}

 void showAchievements(){base("Conquistas",true);content.addView(text("Seu mural",24,Typeface.BOLD,DARK));content.addView(text("Desbloqueie medalhas estudando e praticando.",14,Typeface.NORMAL,MUTED));content.addView(space(16));achievement("","Primeiro passo","Concluir 1 lição",prefs.getInt("completed_lessons",0)>=1);achievement("","Em chamas","Alcançar sequência de 3 dias",streak>=3);achievement("","XP 500","Ganhar 500 XP",xp>=500);achievement("","Persistente","Concluir 10 lições",prefs.getInt("completed_lessons",0)>=10);achievement("","Pythonista","Concluir Python",prefs.getInt("done_Python",0)>=courses.get("Python").length);achievement("","Poliglota","Começar 5 linguagens",startedLanguages()>=5);}
 void achievement(String icon,String title,String desc,boolean unlocked){LinearLayout c=card();c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(dp(16),dp(14),dp(16),dp(14));c.addView(text(unlocked?icon:"",25,Typeface.BOLD,unlocked?GOLD:MUTED),new LinearLayout.LayoutParams(dp(52),dp(52)));LinearLayout m=new LinearLayout(this);m.setOrientation(LinearLayout.VERTICAL);m.addView(text(title,17,Typeface.BOLD,unlocked?DARK:MUTED));m.addView(text(desc+(unlocked?" • desbloqueada":""),13,Typeface.NORMAL,MUTED));c.addView(m,new LinearLayout.LayoutParams(0,-2,1));content.addView(c);content.addView(space(10));}
 int startedLanguages(){int n=0;for(String l:courses.keySet())if(prefs.getInt("done_"+l,0)>0)n++;return n;}

 void showRanking(){base("Ranking semanal",true);content.addView(text("Liga Byte",24,Typeface.BOLD,DARK));content.addView(text("Ranking local demonstrativo. Seu XP real define sua posição entre perfis simulados.",14,Typeface.NORMAL,MUTED));content.addView(space(16));ArrayList<String[]> rows=new ArrayList<>();rows.add(new String[]{"Luna","1320"});rows.add(new String[]{"Rafael","980"});rows.add(new String[]{username,String.valueOf(xp)});rows.add(new String[]{"Maya","620"});rows.add(new String[]{"Theo","410"});Collections.sort(rows,(a,b)->Integer.parseInt(b[1])-Integer.parseInt(a[1]));for(int i=0;i<rows.size();i++){boolean me=rows.get(i)[0].equals(username);LinearLayout c=card();c.setPadding(dp(16),dp(14),dp(16),dp(14));c.setGravity(Gravity.CENTER_VERTICAL);c.addView(text((i+1)+"º",18,Typeface.BOLD,i<3?GOLD:MUTED),new LinearLayout.LayoutParams(dp(48),dp(46)));c.addView(text((me?"":"")+rows.get(i)[0],17,Typeface.BOLD,me?PURPLE:DARK),new LinearLayout.LayoutParams(0,dp(46),1));c.addView(text(rows.get(i)[1]+" XP",14,Typeface.BOLD,DARK));content.addView(c);content.addView(space(9));}}

 void checkAchievements(){int count=0;if(prefs.getInt("completed_lessons",0)>=1)count++;if(streak>=3)count++;if(xp>=500)count++;if(prefs.getInt("completed_lessons",0)>=10)count++;if(prefs.getInt("done_Python",0)>=courses.get("Python").length)count++;if(startedLanguages()>=5)count++;prefs.edit().putInt("achievement_count",count).apply();}

 void updateStreak(){String today=today(),last=prefs.getString("last_day","");if(!today.equals(last)){Calendar c=Calendar.getInstance();c.add(Calendar.DAY_OF_YEAR,-1);String y=new SimpleDateFormat("yyyy-MM-dd",Locale.US).format(c.getTime());streak=last.equals(y)?prefs.getInt("streak",0)+1:1;prefs.edit().putInt("streak",streak).putString("last_day",today).apply();}}
 String today(){return new SimpleDateFormat("yyyy-MM-dd",Locale.US).format(new Date());}
 String iconFor(String l){if(l.equals("Python"))return "Py";if(l.equals("JavaScript"))return "JS";if(l.equals("Java"))return "J";if(l.equals("C++"))return "C++";if(l.equals("C#"))return "C#";if(l.equals("Kotlin"))return "Kt";if(l.equals("SQL"))return "SQL";if(l.equals("HTML/CSS"))return "</>";if(l.equals("PHP"))return "PHP";return "Go";}

 void bounce(View v){ObjectAnimator a=ObjectAnimator.ofFloat(v,"translationY",0f,-dp(7),0f);a.setDuration(1200);a.setRepeatCount(ObjectAnimator.INFINITE);a.start();}
 LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.HORIZONTAL);l.setBackground(round(Color.WHITE,18,Color.rgb(231,233,242)));l.setElevation(dp(2));return l;}
 LinearLayout cardVertical(){LinearLayout l=card();l.setOrientation(LinearLayout.VERTICAL);return l;}
 GradientDrawable round(int bg,int radius,int stroke){GradientDrawable g=new GradientDrawable();g.setColor(bg);g.setCornerRadius(dp(radius));if(stroke!=Color.TRANSPARENT)g.setStroke(dp(1),stroke);return g;}
 Button button(String s,int bg,int fg){Button b=new Button(this);b.setText(s);b.setTextColor(fg);b.setTextSize(14);b.setTypeface(Typeface.DEFAULT,Typeface.BOLD);b.setAllCaps(false);b.setBackground(round(bg,14,Color.rgb(224,226,236)));return b;}
 TextView section(String s){TextView t=text(s,20,Typeface.BOLD,DARK);t.setPadding(0,dp(24),0,dp(12));return t;}
 TextView text(String s,float size,int style,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setTypeface(Typeface.DEFAULT,style);t.setGravity(Gravity.CENTER_VERTICAL);t.setLineSpacing(0,1.12f);return t;}
 View space(int h){Space s=new Space(this);s.setLayoutParams(new LinearLayout.LayoutParams(1,dp(h)));return s;}
 View spaceW(int w){Space s=new Space(this);s.setLayoutParams(new LinearLayout.LayoutParams(dp(w),1));return s;}
 int dp(int v){return (int)(v*getResources().getDisplayMetrics().density+0.5f);}
}
