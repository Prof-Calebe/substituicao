# Sistema de substituição de professores.[![Build Status](https://travis-ci.org/Prof-Calebe/substituicao.svg?branch=master)](https://travis-ci.org/Prof-Calebe/substituicao)[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c62eff64edd34b2d804dafa3e8f333b9)](https://www.codacy.com/app/calebepb/substituicao?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Prof-Calebe/substituicao&amp;utm_campaign=Badge_Grade)[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/c62eff64edd34b2d804dafa3e8f333b9)](https://www.codacy.com/app/calebepb/substituicao?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Prof-Calebe/substituicao&amp;utm_campaign=Badge_Coverage)

Sistema para controle de professores substitutos.

### Issues

Qualquer tarefa ou problema deve ser reportado como uma Issue **antes** de executar qualquer coisa, seja a realização de
uma tarefa, ou a correção de um problema.

Além disso, um desenvolvedor somente poderá trabalhar na Issue quando ela for classificada (bug, enhancement, etc) e/ou
comentada pelo proprietário ou algum administrador do repositório.

### Wiki

Qualquer alteração na Wiki somente será feita depois de uma Issue específica para isso (veja item anterior) e de sua
discussão/explicação por meio dos comentários da própria Issue.

### Git & Branches

O correto uso de git é fundamental. Assim, evitando problemas futuros, as branches `master` e `dev` estão bloqueadas
para `push` e somente serão atualizadas por meio de `pull requests`. Estes serão inspecionados **por todos** os
desenvolvedores e, caso algum problema seja encontrado, deverá ser corrigido antes de ser aceito - isso será feito tanto
nos comentários das issues quanto nos pull-request.

Assim, utilizaremos [No Switch Yard (NoSY)](http://geant.cern.ch/content/suggested-work-flow-distributed-projects-nosy)
como workflow, além de usar um [Git Branch Model](http://nvie.com/posts/a-successful-git-branching-model/) específico
para criação de `branches` e `pull requests`.

Para facilitar um pouco esse entendimento, segue um **exemplo prático** de uso no NoSY e do Git Branch Model (com
algumas padronização de nomes):

* Faça a sua `branch` a partir da `dev`, substituindo **##** pelo número da issue que foi documentada (com o hífen).

```bash
$ git checkout -b [issue-##] remotes/origin/dev
```

* A partir de então, faça as alterações e sempre realize `commit` para marcar a evolução da correção.

```bash
$ git add # arquivos
$ git commit # com os seus commits
```

Lembre-se que um `commit` pode abranger mais de um arquivo, portanto adicione quantos arquivos forem necessários para
caracterizar o seu `commit`.

* Uma vez finalizada as alterações, sincronize sua `branch` com a `dev`.

```bash
$ git checkout dev
$ git pull
$ git checkout [issue-##]
$ git rebase origin/dev
```

* Se não tiver conflitos, envie suas alterações para o repositório.

```bash
$ git push origin HEAD
```

A patir desse momento, a sua nova branch deve aparecer no repositório.

* Aguarde o build e demais hooks avaliarem a `branch`. Caso nenhuma falha seja encontrada, faça um `pull-request` da
  `branch-##` para a `dev` e aguarde os comentários da revisão - alguns hooks farão comentários automáticos neste
  `pull-request` e, portanto, as anotações deverão ser corrigidas e/ou explicadas.

* Caso seja necessário alterar a sua `branch` devido a alguma falha do build, dos hooks, ou dos comentários de revisão,
  faça-os normalmente na sua branch `issue-##`, sincronizando-a novamente ao final das mudanças e reenviando-a para o
  repositório. Aguarde os resultados descritos no passo anterior e, se for o caso, repita todo este processo. Se um
  pull-request já foi feito, não é necessário refazê-lo ou fechá-lo.

```bash
$ git checkout [issue-##]
$ git add # arquivos
$ git commit # com os seus commits
$ git rebase origin/dev
$ git push --force origin HEAD
```

A primeira linha de uma mensagem de `commit` deve ser simples, precisa e significativa e, se possível, conter no máximo
50 caracteres. Se for necessária uma mansagem maior, resuma o problema corrigido na **1a linha** e a partir da **3a
linha** (_terceira linha_) da mensagem explique com mais detalhes o `commit`, mantendo 120 caracteres por linha. Quando
pertinente e possível, utilize [auto-referências](https://help.github.com/articles/autolinked-references-and-urls/) às
issues e desenvolvedores, e mensagens com
[palavras-chaves](https://help.github.com/articles/closing-issues-via-commit-messages/).


### Hooks

Alguns serviços automáticos foram associados a este repositório:
- [Travis-CI](https://travis-ci.org/Prof-Calebe/substituicao)
- [Codacy](https://www.codacy.com/app/calebepb/substituicao/dashboard)

**Muito cuidado** ao manipular o hook do Codacy, pois ele utiliza uma variável de ambiente com uma _chave secreta_ para
enviar o relatório de code coverity para o dashborad. Se essa chave for pública, o dashboard ficará comprometido.

