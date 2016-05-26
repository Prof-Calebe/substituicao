# Sistema de substituição de professores. [![Codacy Badge](https://api.codacy.com/project/badge/Grade/5baebcd1bb1b4dfb9016a7fd1e5b5200)](https://www.codacy.com/app/calebepb/substituicao?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Prof-Calebe/substituicao&amp;utm_campaign=Badge_Grade)

Sistema para controle de professores substitutos.

### Issues

Qualquer tarefa ou problema deve ser reportado como uma Issue **antes** de executar qualquer coisa, seja a realização de uma tarefa, ou a correção de um problema.

Além disso, um desenvolvedor somente poderá trabalhar na Issue quando ela for classificada (bug, enhancement, etc) e/ou comentada pelo dono do produto ou usuário.

### Wiki

Qualquer alteração na Wiki somente será feita depois de uma Issue específica para isso (veja item anterior) e de sua discussão/explicação por meio dos comentários da própria Issue.

### Git & Branches

O correto uso de git é fundamental. Assim, evitando problemas futuros, as branches `master` e `dev` estão bloqueadas para `push` e somente serão atualizadas por meio de `pull requests`. Estes serão inspecionados **por todos** os desenvolvedores e, caso algum problema seja encontrado, deverá ser corrigido antes de ser aceito.

Assim, utilizaremos [No Switch Yard (NoSY)](http://geant.cern.ch/content/suggested-work-flow-distributed-projects-nosy) como workflow, além de usar um [Git Branch Model](http://nvie.com/posts/a-successful-git-branching-model/) específico para criação de `branches` e `pull requests`.

### Hooks

Alguns serviços automáticos foram associados a este repositório:
- [Travis-CI](https://travis-ci.org/Prof-Calebe/substituicao)
- [Codacy](https://www.codacy.com/app/calebepb/substituicao/dashboard)

**Muito cuidado** ao manipular o hook do Codacy, pois ele utiliza uma variável de ambiente com uma _chave secreta_ para enviar o relatório de code coverity para o dashborad. Se essa chave for pública, o dashboard ficará comprometido.

