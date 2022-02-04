Projekt Idee : Office meetings manager

Beschreibung : wie der Name schon sagt , der Hauptanwendungsfall
ist die Organisation und Einstellung von Meetings zwischen denselben
Mitarbeitern eines Unternehmens "X" .
Jeder Mitarbeiter hat ein eigenes Konto, mit dem er sich in die
App einloggen kann. Das Konto wird automatisch generiert, wenn
ein Mitarbeiter dem Unternehmen beitritt (wie zum Beispiel die
htw-Webmail-Zugangsdaten).

einige Funktionen, die ich in die App implementieren möchte :
"Login"
"Profile Menu(zum Daten verwalten)"
"Meetings Hinzufügen(Zeit/Ort/Teilnehemer..)"
"Meeting options/Daten ändern)"
"Meeting discussion"
PS: weitere Features können hinzugefügt werden, wenn noch Zeit gibt.

Projektmitglieder : Rayen Bedoui(s0570768)


Anmerkungen :

-Ich habe den bug beim registrierung behoben : es gab ein error wenn kein Bild
hinzugefügt wurde .Dafür habe Ich ein boolean erstellt , das überprüft, ob ein 
Bild hinzugefügt wurde oder nicht .

-wie ich während der Präsentation gesagt habe , hatte ich leider keine Zeit 
 folgende Funktionalität zu vervollständigen : add users to the meeeting.
 Also , es ist 80% vollständig (die recycler view ist 100% funktional und 
 daten werden richtig angezeigt )

-es gibt ein bug wenn mann von einem activity zu eine von der zwei recycler views 
 (die Meetings und die users recyclerviews) mit dem Handy Taste zurück geht und Ich 
 könnte das Problem nicht lösen . dafür habe Ich ein cancel button in addMeeting activity
 und ein Back button in der Profile activity erstellt damit kann man den Fehler vermeiden.

-die app kann aufgrund der hohen Qualität der vewendeten Fotos etwas langsam sein .

