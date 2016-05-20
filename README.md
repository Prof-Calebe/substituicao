# Sistema de substituição de professores.

Sistema para controle de professores substitutos.

### Issues

Qualquer tarefa ou problema deve ser reportado como uma Issue **antes** de ser qualquer coisa, seja a realização de uma tarefa, ou a correção de um problema.

Além disso, um desenvolvedor somente poderá trabalhar na Issue quando ela for classificada (bug, enhancement, etc) pelo dono do produto ou usário.

### Wiki

Qualquer alteração na Wiki somente será feita depois de uma Issue específica para isso (veja item anterior) e de sua discussão/explicação por meio dos comentários da Issue.

### Git & Branches

O correto uso de git é fundamental. Assim, evitando problemas futuros, as branches `master` e `dev` estão bloqueadas para `push` e somente serão atualizadas por meio de `pull requests`. Estes serão inspecionados **por todos** os desenvolvedores e, caso algum problema seja encontrado, deverá ser corrigido antes de ser aceito.

Assim, utilizaremos [No Switch Yard (NoSY)](http://geant.cern.ch/content/suggested-work-flow-distributed-projects-nosy) como workflow, além de usar um [modelo específico](http://nvie.com/posts/a-successful-git-branching-model/) para definição de branches.

### Hooks

Alguns serviços automáticos foram associados a este repositório:
- [Travis-CI](https://travis-ci.org/Prof-Calebe/substituicao)
- [codacy](https://www.codacy.com/app/calebepb/substituicao/dashboard)

