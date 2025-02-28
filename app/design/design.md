```mermaid
graph TD
    A[App Entry] --> B[Login Screen]
    B --> |Succes| C[Home Screen]
    B --> |Failed login| B
    C <--> D[Profile Screen]
    C <--> E[Settings Screen]
    C <--> F[About Us Screen]
    C <--> G[Terms & Conditions Screen]
    C <--> H[Programmed Trips]
    C <--> I[Plan New Trip]
    C <--> J[Explore]
    D <--> K[User Preferences]
    H <--> L[Your Trip Details]
