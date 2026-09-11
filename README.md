# CodeLingo 2.0

App Android educacional inspirado em experiências gamificadas de idiomas, mas voltado a programação.

## Conteúdo
- 10 trilhas: Python, JavaScript, Java, C++, C#, Kotlin, SQL, HTML/CSS, PHP e Go.
- 8 lições por linguagem.
- Cada lição tem quiz + laboratório de código, totalizando 160 etapas de exercício.
- XP, nível implícito, meta diária, sequência, 5 vidas, conquistas e ranking local.
- Perfil local com nome do aluno.
- Mascote Byte e animação simples.
- Progresso salvo offline com SharedPreferences.

## Avaliador de código
O app não executa código arbitrário localmente. O laboratório compara a solução com tokens e estrutura esperados. Para uma versão de produção, conecte um sandbox remoto seguro (por exemplo, serviço próprio isolado) e nunca execute código do aluno diretamente no processo Android.

## Compilar no Android Studio
1. Abra esta pasta no Android Studio.
2. Instale Android SDK 35 se solicitado.
3. Aguarde o Gradle sincronizar.
4. Menu `Build > Build APK(s)`.
5. O APK debug ficará em `app/build/outputs/apk/debug/app-debug.apk`.

## Compilar pelo GitHub Actions
O projeto inclui `.github/workflows/build-apk.yml`. Ao subir o projeto para um repositório GitHub e executar o workflow **Build Android APK**, o GitHub gera `CodeLingo-debug-apk` como artifact.

## Observação sobre nuvem
Esta versão é offline-first. Login remoto, ranking entre usuários e sincronização real exigem um backend/autenticação. A arquitetura atual mantém o progresso em chaves estáveis (`xp`, `streak`, `done_<linguagem>`, etc.), facilitando migração posterior para Firebase/Supabase/API própria.
